package com.backend.kfc.wechat;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.backend.kfc.exception.GeneralException;
import com.backend.kfc.service.BillService;
import com.backend.kfc.util.StringUtils;

@EnableAutoConfiguration
@RestController
@RequestMapping("/backend/kfc/wechat")
@CrossOrigin
public class WechatController {

    @Autowired
    private WechatService wechatService;
    @Autowired
    private BillService billService;
    
    @RequestMapping(value = "/qr", method = RequestMethod.POST)
    public String testGetQrOfMiniProgram() throws GeneralException {
        return billService.createAndUploadBillQR("5b1f78af8435710f00a312c3");
    }
    
    @RequestMapping(value = "/message", method = {RequestMethod.POST, RequestMethod.GET})
    public void processWechatMessage(HttpServletRequest request, HttpServletResponse response) throws GeneralException {
        if (RequestMethod.GET.equals(request.getMethod())) {
            //处理确认请求
         // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");
            PrintWriter out;
            try {
                System.out.println("signature : " + signature);
                System.out.println("timestamp : " + timestamp);
                System.out.println("nonce : " + nonce);
                System.out.println("echostr : " + echostr);
                out = response.getWriter();
                // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
                boolean verifiyRet = checkSignature(signature, timestamp, nonce);

                System.out.println("verifiyRet : " + verifiyRet);
                if (verifiyRet) {
                    out.print(echostr);
                }
                
                out.close();
                out = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
           
        } else {
            
        }
    
    }
    
 // 与接口配置信息中的Token要一致
    private static String token = "souvcweixin";

    /**
    * 方法名：checkSignature</br>
    * 详述：验证签名</br>
    * 开发人员：souvc</br>
    * 创建时间：2015-9-29  </br>
    * @param signature
    * @param timestamp
    * @param nonce
    * @return
    * @throws
     */
    public static boolean checkSignature(String signature, String timestamp,String nonce) {
        // 1.将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = new String[] { token, timestamp, nonce };
        Arrays.sort(arr);
        
        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = StringUtils.byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        content = null;
        // 3.将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }
    
}
