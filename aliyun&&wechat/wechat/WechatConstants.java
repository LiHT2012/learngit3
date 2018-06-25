package com.backend.kfc.wechat;

public class WechatConstants {
    public static final String WECHAT_REPLACE_APPID = "APPID";
    public static final String WECHAT_REPLACE_APPSECERT = "APPSECRET";
    public static final String WECHAT_REPLACE_SECRET = "SECRET";
    public static final String WECHAT_REPLACE_CODE = "CODE";
    public static final String WECHAT_REPLACE_JSCODE = "JSCODE";    
    
    public static final String WECHAT_REPLACE_ACCESSTOKEN = "ACCESS_TOKEN";
    public static final String WECHAT_REPLACE_OPENID = "OPENID";
    public static final String WECHAT_REPLACE_NEXT_OPENID = "NEXT_OPENID";
    /**
     * 基础支持获取ACCESSTOKEN（用户关注公众号后可通过基础接口获取相关信息，不需要授权）
     */
    public static final String WECHAT_PUBLIC_ACCESSTOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    
    /**
     * 微信公众平台获取jsapiTicket
     */
    public static final String WECHAT_PUBLIC_JSAPITICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    /**
     * 微信公众平台获取所有关注用户的openId
     */
    public static final String WECHAT_PUBLIC_GET_ALL_OPEN_ID_FIRST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";
    public static final String WECHAT_PUBLIC_GET_ALL_OPEN_ID_CONTINUE = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
    /**
     * 小程序API发送模板消息URL
     */
    public static final String WECHAT_MINIPROGRAM_SEND_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";
    /**
     * 小程序通过临时的code获取session_key及openId;
     */
    public static final String URL_MINIPROGRAM_GET_SESSION_KEY_AND_OPENID = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

    public static final String URL_MIMIPROGRAM_GET_QR_B = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
    
    public static final String APPID_MINIPROGRAM_KFC = "wx0f847cacd0d0da7e";
    public static final String SECRETKEY_MINIPROGRAM_KFC = "0a8c41237d1286bbcb5d68acf5bdaf05";
    
    public static final String FENGCHAO_NEWFLASH_TEMPLATEID = "O61MndiKj_bQXIddjW0AWrD7r4qJ6mvw6ciO_I6n-Zk";

}
