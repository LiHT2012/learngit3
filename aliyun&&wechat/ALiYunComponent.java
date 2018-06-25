package com.backend.kfc.component;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.backend.kfc.util.DateUtil;

@Component
public class ALiYunComponent {
	public static final String END_POINT_HANGZHOU = "oss-cn-hangzhou.aliyuncs.com";//
	private static final String ACCESS_ID = "LTAIHv1zg5lJgphH";
	private static final String ACCESS_KEY = "0qrbBiIVOs52tWVxDrujB3ZwPbUAl2";
	private static final String BUCKET_PIC = "oilbackend-pic";
	private static final String HOST_PIC = "http://" + BUCKET_PIC + "." + END_POINT_HANGZHOU + "/";
	private static final OSSClient CLIENT = new OSSClient(END_POINT_HANGZHOU,
			new DefaultCredentialProvider(ACCESS_ID, ACCESS_KEY), new ClientConfiguration());
	private static final Long EXPIRE_TIME = 7200000L;
	private static final Integer RANGE_START = 0;
	private static final Integer RANGE_END = 1048576000;

	public Map<String, String> getOssSign(String account) throws UnsupportedEncodingException {
		Map<String, String> respMap = new HashMap<>();
		Date expiration = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		PolicyConditions policyConds = new PolicyConditions();
		policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, RANGE_START, RANGE_END);
		policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, account);
		String postPolicy = CLIENT.generatePostPolicy(expiration, policyConds);
		byte[] binaryData = postPolicy.getBytes("utf-8");
		String encodedPolicy = BinaryUtil.toBase64String(binaryData);
		String postSignature = CLIENT.calculatePostSignature(encodedPolicy);
		respMap.put("accessid", ACCESS_ID);
		respMap.put("policy", encodedPolicy);
		respMap.put("signature", postSignature);
		respMap.put("dir", account);
		respMap.put("host", HOST_PIC);
		respMap.put("expire", String.valueOf(expiration.getTime() / 1000));
		return respMap;
	}

	public String uploadPic(String account, String fileName, InputStream input, String contentType) {
		String key = account + "_" + fileName + "_" + DateUtil.getFormatDateForSpecial();
		key = key.replaceAll("/", "");
		uploadObj(BUCKET_PIC, key, input, contentType);
		return HOST_PIC + key;
	}
	
	public void uploadObj(String bucket, String key, InputStream input, String contentType) {
	    ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
	    CLIENT.putObject(bucket, key, input, objectMetadata);
	    return;
	}

	public URL getPicUrl(String key) {
		URL url = CLIENT.generatePresignedUrl(BUCKET_PIC, key, new Date(System.currentTimeMillis() + EXPIRE_TIME));
		return url;
	}

}
