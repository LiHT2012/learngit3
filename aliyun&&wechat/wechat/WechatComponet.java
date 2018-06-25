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
import com.backend.kfc.util.TwoTuple;
import com.backend.kfc.wechat.dto.MiniProgramGetSessionKeyDTO;
import com.backend.kfc.wechat.dto.WechatAccessTokenDto;
import com.backend.kfc.wechat.dto.WechatAllOpenIdsDTO;
import com.backend.kfc.wechat.model.MiniProgramTemplateMessage;

@Component
public class WechatComponet {

	@Autowired
	private SendTemplateThreadManager threadManager;

	private CloseableHttpClient httpClient = HttpClientBuilder.create().build();

	@Cacheable(value = "wechat:accesstoken", key = "#appId", cacheManager = "accessTokenCacheManager")
	public String getAccessTokenWithAppIdAndSecretKey(String appId, String secretKey) {
		String url = WechatConstants.WECHAT_PUBLIC_ACCESSTOKEN.replace(WechatConstants.WECHAT_REPLACE_APPID, appId)
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

	public String getjsapiTicket(String accessToken, String appId, String secretKey) throws GeneralException {
		String url = WechatConstants.WECHAT_PUBLIC_JSAPITICKET.replace(WechatConstants.WECHAT_REPLACE_ACCESSTOKEN,
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
	public List<String> getAllOpenIds(String accessToken, String appId, String secretKey) throws GeneralException {
		// 首次读取所有的OPENID
		String url = WECHAT_PUBLIC_GET_ALL_OPEN_ID_FIRST.replace(WECHAT_REPLACE_ACCESSTOKEN, accessToken);
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
		List<String> openIds = new ArrayList<>(dto.getData().getOpenid());
		// 如果读取的数量小于总数量，则继续读取
		int count = dto.getCount();
		int errtimes = 0;
		while (count < dto.getTotal()) {
			url = WECHAT_PUBLIC_GET_ALL_OPEN_ID_CONTINUE.replace(WECHAT_REPLACE_ACCESSTOKEN, accessToken);
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
			count += dto.getCount();
			openIds.addAll(dto.getData().getOpenid());
		}

		return openIds;
	}

	public void sendTemplateMessageAsMiniProgram(String accessToken, MiniProgramTemplateMessage message) {
		String url = WECHAT_MINIPROGRAM_SEND_TEMPLATE_MESSAGE.replace(WECHAT_REPLACE_ACCESSTOKEN, accessToken);
		threadManager.sendMessage(message, url);
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
	public TwoTuple<String, String> miniProgramGetSessionKeyAndOpenIdByCode(String jsCode, String appId,
			String appSecret) throws GeneralException {
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
		TwoTuple<String, String> ret = null;
		if (null != result && result.getErrcode() == null) {
			ret = new TwoTuple<String, String>(result.getOpenid(), result.getSession_key());
		} else {
			throw new GeneralException(
					"get openId failed, errcode : " + result.getErrcode() + " errmsg : " + result.getErrmsg());
		}
		return ret;
	}

	public InputStream getminiqrQr(String accessToken, String sceneStr, String page) throws GeneralException {
		try {
			String url = URL_MIMIPROGRAM_GET_QR_B + accessToken;
			Map<String, Object> param = new HashMap<>();
			param.put("scene", sceneStr);
			// param.put("page", page);
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
			StringEntity entity = new StringEntity(body);
			entity.setContentType("image/png");

			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			return response.getEntity().getContent();
		} catch (Exception e) {
			throw new GeneralException("调用小程序生成微信永久小程序码URL接口异常" + e.getMessage());
		}
	}

}
