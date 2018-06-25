package com.backend.kfc.controller;

//import java.io.File;
//import java.io.FileInputStream;
import java.io.IOException;
//import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.kfc.cfg.SystemUrlCfg;
import com.backend.kfc.component.ALiYunComponent;
import com.backend.kfc.dto.response.SimpleResponse;
import com.backend.kfc.exception.GeneralException;
import com.backend.kfc.exception.UserNotLoginException;
import com.backend.kfc.model.mysql.OilUser;

@EnableAutoConfiguration
@RestController
@RequestMapping("/backend/oss")
@CrossOrigin
public class ALiYunController {
	@Autowired
	private ALiYunComponent aLiYunComponent;

	@RequestMapping(value = "/sign")
	public String getAliYunOssSign(HttpServletRequest request, HttpServletResponse response) throws GeneralException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST");
		try {
			Map<String, String> map = aLiYunComponent
					.getOssSign((String) request.getAttribute(SystemUrlCfg.LOGIN_USER));
			return SimpleResponse.simpleResponseMap(map);
		} catch (UnsupportedEncodingException e) {
			throw new GeneralException("sign wrong");
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String updateFile(@RequestBody MultipartFile file, HttpServletRequest request) throws IOException, UserNotLoginException {
		HttpSession session = request.getSession();
		OilUser oilUser = (OilUser) session.getAttribute(SystemUrlCfg.LOGIN_USER);
		if (null == oilUser) {
			throw new UserNotLoginException();
		}
		String key = aLiYunComponent.uploadPic(oilUser.getUserId(),
				file.getOriginalFilename(), file.getInputStream(),file.getContentType());
		return SimpleResponse.simpleResponseId(key);
//		InputStream  inputstream  = new  FileInputStream("/home/dbc-intern5/test.adoc");
//		String key2 = aLiYunComponent.uploadPic(oilUser.getUserId(),
//				"test", inputstream,".adoc");
//		return SimpleResponse.simpleResponseId(key2);
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public String downloadFile(@RequestBody Map<String,String> map) throws IOException {
		URL uri = aLiYunComponent.getPicUrl(map.get("key")); 
		return SimpleResponse.simpleResponseObject(uri);
	}

}
