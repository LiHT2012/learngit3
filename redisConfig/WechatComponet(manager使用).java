package com.backend.kfc.wechat;

import static com.backend.kfc.wechat.WechatConstants.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.backend.kfc.exception.GeneralException;
import com.backend.kfc.util.JacksonUtil;
import com.backend.kfc.util.RestClient;
import com.backend.kfc.wechat.dto.BatchGetWechatUserInfoRequestDTO;
import com.backend.kfc.wechat.dto.BatchGetWechatUserInfoResponseDTO;
import com.backend.kfc.wechat.dto.MiniProgramGetSessionKeyDTO;
import com.backend.kfc.wechat.dto.WechatAccessTokenDto;
import com.backend.kfc.wechat.dto.WechatAllOpenIdsDTO;
import com.backend.kfc.wechat.dto.WechatPlatformUserInfoDTO;
import com.backend.kfc.wechat.model.MiniProgramTemplateMessage;
import com.backend.kfc.wechat.model.WechatTemplateMessage;
import com.backend.kfc.wechat.model.WechatUserUnionIdInfo;
import com.backend.kfc.wechat.threadpools.SendHttpRequestToWechatThreadManager;

@Component
public class WechatComponet {

	@Autowired
	private SendHttpRequestToWechatThreadManager threadManager;

	private CloseableHttpClient httpClient = HttpClientBuilder.create().build();

	@Cacheable(value = "wechat:accesstoken:", key = "#tag", cacheManager = "accessTokenCacheManager")
	public String getAccessTokenWithAppIdAndSecretKey(String tag, String appId, String secretKey) {
		String url = WechatConstants.URL_WECHAT_PUBLIC_ACCESSTOKEN.replace(WechatConstants.WECHAT_REPLACE_APPID, appId)
				.replace(WechatConstants.WECHAT_REPLACE_APPSECERT, secretKey);
		String res = new RestTemplate().getForObject(url, String.class);
		if (null == res) {
			return null;
		}
		WechatAccessTokenDto wechatAccessTokenDto = JacksonUtil.json2Obj(res, WechatAccessTokenDto.class);

		if (null != wechatAccessTokenDto) {
			if (null == wechatAccessTokenDto.getErrcode()) {
				return wechatAccessTokenDto.getAccess_token();
			}
		}
		return null;
	}
	@Cacheable(value = "wechat:accesstoken:", key = "#tag", cacheManager = "accessTokenCacheManager")
	public String test(String tag, String appId, String secretKey) {
		System.out.println("hahah");
		return "test";
	}

	public String getjsapiTicket(String accessToken, String appId, String secretKey) throws GeneralException {
		String url = WechatConstants.URL_WECHAT_PUBLIC_JSAPITICKET.replace(WechatConstants.WECHAT_REPLACE_ACCESSTOKEN,
				accessToken);
		String res = RestClient.getForObject(url, String.class);
		WechatAccessTokenDto wechatAccessTokenDto = JacksonUtil.json2Obj(res, WechatAccessTokenDto.class);
		if (0 != wechatAccessTokenDto.getErrcode()) {
			throw new GeneralException("获取微信公众号jsapiTicket失败");
		}
		return wechatAccessTokenDto.getTicket();
	}

	/**
	 * 获取制定公众号下所有关注用户的ID
	 * 
	 * @return
	 * @throws GeneralException
	 */
	public List<String> getAllOpenIdsFromFirst(String accessToken) throws GeneralException {
		// 首次读取所有的OPENID
		String url = URL_WECHAT_PUBLIC_GET_ALL_OPEN_ID_FIRST.replace(WECHAT_REPLACE_ACCESSTOKEN, accessToken);
		return doGetAllOpenIds(accessToken, url);
	}

	/**
	 * 从指定的openId获取之后的openId
	 * 
	 * @param accessToken
	 * @param nextOpenId
	 * @return
	 * @throws GeneralException
	 */
	public List<String> getAllOpenIdsFromNextOpenId(String accessToken, String nextOpenId) throws GeneralException {
		String url = URL_WECHAT_PUBLIC_GET_ALL_OPEN_ID_CONTINUE.replace(WECHAT_REPLACE_ACCESSTOKEN, accessToken)
				.replaceAll(WECHAT_REPLACE_NEXT_OPENID, nextOpenId);
		return doGetAllOpenIds(accessToken, url);
	}

	private List<String> doGetAllOpenIds(String accessToken, String url) throws GeneralException {
		String res;
		try {
			res = RestClient.getForObject(url, String.class);
		} catch (RestClientException e) {
			throw new GeneralException("get all openids failed");
		}
		WechatAllOpenIdsDTO dto = JacksonUtil.json2Obj(res, WechatAllOpenIdsDTO.class);
		if (dto.getErrcode() != null) {
			throw new GeneralException("get all openids failed");
		}
		int count = dto.getCount();
		if (count == 0) {
			return null;
		}
		List<String> openIds = new ArrayList<>(dto.getData().getOpenid());
		int errtimes = 0;
		String nextOpenId = dto.getNext_openid();
		// 如果读取的数量等于10000且nextOpenId不为null，则继续读取
		while (count == 10000 && StringUtils.isNotBlank(nextOpenId)) {
			url = URL_WECHAT_PUBLIC_GET_ALL_OPEN_ID_CONTINUE.replace(WECHAT_REPLACE_ACCESSTOKEN, accessToken)
					.replaceAll(WECHAT_REPLACE_NEXT_OPENID, nextOpenId);
			try {
				res = new RestTemplate().getForObject(url, String.class);
			} catch (RestClientException e) {
				// 如果某一次查询出错，则继续
				errtimes++;
				if (errtimes == 5) {
					// 连续出错5次退出循环
					break;
				}
				continue;
			}

			dto = JacksonUtil.json2Obj(res, WechatAllOpenIdsDTO.class);
			if (dto.getErrcode() != null) {
				// 如果某一次查询出错，则继续
				errtimes++;
				if (errtimes == 5) {
					// 连续出错5次退出循环
					break;
				}
				continue;
			}
			errtimes = 0;
			count = dto.getCount();
			nextOpenId = dto.getNext_openid();
			if (count != 0) {
				openIds.addAll(dto.getData().getOpenid());
			}
		}

		return openIds;
	}

	public void sendTemplateMessageAsMiniProgram(String accessToken, MiniProgramTemplateMessage message) {
		String url = URL_MINIPROGRAM_SEND_TEMPLATE_MESSAGE.replace(WECHAT_REPLACE_ACCESSTOKEN, accessToken);
		threadManager.sendHttpRequest(message, url);
		return;
	}

	public void sendWechatTemplateMessage(String accessToken, WechatTemplateMessage message) {
		String url = URL_WECHAT_COMMON_SEND_TEMPLATE_MESSAGE.replace(WECHAT_REPLACE_ACCESSTOKEN, accessToken);
		threadManager.sendHttpRequest(message, url);
		return;
	}

	/**
	 * 小程序通过临时code获取sessionKey及openId
	 * 
	 * @param jsCode微信端登录code值
	 * @param appId
	 * @param appSecret
	 * @return
	 * @throws GeneralException
	 */
	public WechatUserUnionIdInfo miniProgramGetSessionKeyAndOpenIdByCode(String jsCode, String appId, String appSecret)
			throws GeneralException {
		MiniProgramGetSessionKeyDTO result = miniProgramGetSessionKeyAndIdsByCode(jsCode, appId, appSecret);
		WechatUserUnionIdInfo ret = null;
		if (null != result && result.getErrcode() == null) {
			ret = new WechatUserUnionIdInfo(result.getOpenid(), result.getUnionid());
		} else {
			throw new GeneralException(
					"get openId failed, errcode : " + result.getErrcode() + " errmsg : " + result.getErrmsg());
		}
		return ret;
	}
	public MiniProgramGetSessionKeyDTO miniProgramGetSessionKeyAndIdsByCode(String jsCode, String appId, String appSecret)
			throws GeneralException {
		String url = URL_MINIPROGRAM_GET_SESSION_KEY_AND_OPENID.replace(WECHAT_REPLACE_APPID, appId)
				.replace(WECHAT_REPLACE_SECRET, appSecret).replace(WECHAT_REPLACE_JSCODE, jsCode);
		String res = null;
		try {
			res = RestClient.getForObject(url, String.class);
		} catch (RestClientException e) {
			throw new GeneralException("get sessionKey and openId failed");
		}
		if (StringUtils.isBlank(res)) {
			throw new GeneralException("get sessionKey and openId failed");
		}
		MiniProgramGetSessionKeyDTO result = JacksonUtil.json2Obj(res, MiniProgramGetSessionKeyDTO.class);
		if (null != result && result.getErrcode() == null) {
			return result;
		} else {
			throw new GeneralException(
					"get openId failed, errcode : " + result.getErrcode() + " errmsg : " + result.getErrmsg());
		}
	}

	public InputStream getminiqrQr(String accessToken, String sceneStr, String page) throws GeneralException {
		try {
			String url = URL_MIMIPROGRAM_GET_QR_B + accessToken;
			Map<String, Object> param = new HashMap<>();
			param.put("scene", sceneStr);
			 param.put("page", page);
			param.put("width", 430);
			param.put("auto_color", true);
			// Map<String,Object> line_color = new HashMap<>();
			// line_color.put("r", 0);
			// line_color.put("g", 0);
			// line_color.put("b", 0);
			// param.put("line_color", line_color);
			// System.out.println("调用生成微信URL接口传参:" + JacksonUtil.obj2Json(param));
			// System.out.println("调用生成微信URL:" + url);

			HttpPost httpPost = new HttpPost(url); // 接口
			httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
			String body = JacksonUtil.obj2Json(param); // 必须是json模式的 post
			System.out.println(body);
			StringEntity entity = new StringEntity(body);
			entity.setContentType("image/png");

			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			return response.getEntity().getContent();
		} catch (Exception e) {
			throw new GeneralException("调用小程序生成微信永久小程序码URL接口异常" + e.getMessage());
		}
	}

	/**
	 * 获取openId对应unionId，如果用户未关注相应的平台，则不会返回其对应的openId或者unionId
	 * 
	 * @param accessToken
	 * @param openIds
	 * @return 除非openIds为null否则不为null。查询无结果时，返回空集合
	 * @throws GeneralException
	 */
	public List<WechatUserUnionIdInfo> batchGetWechatUserInfo(String accessToken, List<String> openIds)
			throws GeneralException {
		if (null == openIds || openIds.isEmpty()) {
			return null;
		}
		String url = URL_WECHAT_BATCH_GET_USER_COMMON_INFO.replace(WECHAT_REPLACE_ACCESSTOKEN, accessToken);
		int userCount = openIds.size();
		List<WechatUserUnionIdInfo> ret = new ArrayList<WechatUserUnionIdInfo>(openIds.size());

		if (userCount <= NUM_BATCH_GET_USER_ONCE) {
			// 100次以内，直接调用，不做过多判断
			doPostBatchGetWechatUserInfo(url, openIds, ret);
		} else {
			int times = userCount / NUM_BATCH_GET_USER_ONCE;
			List<String> subOpenIds = null;

			for (int i = 0; i <= times; i++) {
				subOpenIds = openIds.subList(i * NUM_BATCH_GET_USER_ONCE,
						i == times ? userCount : (i + 1) * NUM_BATCH_GET_USER_ONCE);
				doPostBatchGetWechatUserInfo(url, subOpenIds, ret);
			}
		}
		return ret;
	}

	private void doPostBatchGetWechatUserInfo(String url, List<String> openIds, List<WechatUserUnionIdInfo> ret)
			throws GeneralException {
		BatchGetWechatUserInfoRequestDTO reqParam = new BatchGetWechatUserInfoRequestDTO(openIds);

		BatchGetWechatUserInfoResponseDTO response = null;
		try {
			response = RestClient.postForEntity(url, JacksonUtil.obj2Json(reqParam),
					BatchGetWechatUserInfoResponseDTO.class);
		} catch (RestClientException e) {
			throw new GeneralException("batch get wechat userinfo  failed : " + e.getMessage());
		}
		if (response == null) {
			throw new GeneralException("batch get wechat userinfo  failed : returned object is null");
		}
		if (response.getErrcode() != null && !response.getErrcode().equals(0)) {
			throw new GeneralException(
					"batch get wechat userinfo  failed :" + response.getErrcode() + "," + response.getErrmsg());
		}
		List<WechatPlatformUserInfoDTO> userInfoList = response.getUser_info_list();
		if (null != userInfoList && !userInfoList.isEmpty()) {
			for (WechatPlatformUserInfoDTO userInfo : userInfoList) {
				// subscribe:1表示用户关注了该公众号
				if (userInfo.getSubscribe().equals(1)) {
					ret.add(new WechatUserUnionIdInfo(userInfo.getOpenid(), userInfo.getUnionid()));
				}
			}
		}
	}

}
