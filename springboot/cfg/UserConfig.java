package com.dbcool.api.cfg;

public class UserConfig extends SystemUrlCfg {

	/**
	 * 用户操作成功 status
	 */
	public static final String OPERATE_SUCCECC_STATUS = "1";
	
	/**
	 * 用户操作失败 status
	 */
	public static final String OPERATE_FAIL_STATUS = "0";
	
	/**
	 * redis 存储验证码过期时间 1小时
	 */
	public static final int REDIS_EXPIRE_TIME = 1 * 60  * 60;
	
	/**
	 * redis 验证码发送5次过期时间 1天
	 */
	public static final int REDIS_EXPIRENUM_TIME = 24 * 60 * 60;
	
	public static final int WECHAT_REDIS_TIME = 2 * 60 * 60 - 1;
	
	/**
	 * redis 验证码一天发送次数 5次
	 */
	public static final int REDIS_EXPIRE_NUM = 5;
	
	/**
	 * redis 验证码ip 发送的次数
	 */
	public static final int REDIS_IP_NUM = 100;
	
	/**
	 * redis 返回值
	 */
	public static final String REDIS_SET_RETURN = "OK";
	public static final Long REDIS_DEL_RETURN  = 1L;
	
	/**
	 * redis 计数默认值 1
	 */
	public static final String REDIS_DEFAULT_NUM = "1";
	
	/**
	 * redis 计数默认名称 Time
	 */
	public static final String REDIS_DEFAULT_NAME = "Time";
	
	/**
	 * 验证码随机数位数
	 */
	public static final Integer REDIS_CODE_NUM = 6;
	
	/**
	 * 用户名随机数位数
	 */
	public static final Integer USERNAME_NUM_RANDOM = 8;
	
	public static final Integer USERNAME_CHAR_RANDOM = 2;
	
	/**
	 * 发送验证码类型
	 */
	public static final String CODE_TYPE_REGISTER = "register";
	public static final String CODE_TYPE_RESET = "resetPassword";
	public static final String CODE_TYPE_BIND = "bind";
	
	/**
     * 系统类型
     */
    public static final String SYSTEM_TYPE_FACTUBE = "数据道";
    
	/**
	 * 验证码错误
	 */
	public static final String CODE_VALIDATE_FAIL = "22";
	
	/**
	 * 用户状态
	 */
	public static final Integer USER_STATUS_LOGIN = 0;
	
	public static final String USER_ACCOUNT_EXSIT = "00";
	
	public static final String USER_ACCOUNT_NOTEXSIT = "11";
	
	public static final String NAME_TYPE_USERNAME = "userName";
	public static final String NAME_TYPE_NICKNAME = "nickName";
	
	public static final String UPDATE_USERNAME_EXIST = "000";
	public static final String UPDATE_PHONE_EXIST = "111";
	public static final String UPDATE_EMAIL_EXIST = "222";
	
	/**
     * 查询获取用户途径
     * 从本系统内部
     */
    public static final String SYSTEM_IN_ROUTE = "1";
    /**
     * 查询获取用户途径
     * 远程从用户系统查询获得
     */
    public static final String SYSTEM_OUT_ROUTE = "2";
	
	public static final String EMAIL_REGISTER_CONTENT = "注册验证码为：code。1个小时内有效。如非本人操作，请忽略本条邮件。 ";
	public static final String EMAIL_REGISTER_TITLE = "数据道用户注册";
	public static final String EMAIL_PASSWORDRESET_CONTENT  = "验证码为：code。1个小时内有效。如非本人操作，请忽略本条邮件。";
	public static final String EMAIL_PASSWORDRESET_TITLE = "数据道找回密码";
	public static final String EMAIL_BIND_CONTENT  = "验证码为：code。1个小时内有效。如非本人操作，请忽略本条邮件。";
	public static final String EMAIL_BIND_TITLE = "数据道绑定邮箱";
	
	public static final String PHONE_REGISTER_CONTENT  = "【数据道】注册验证码为：code。1个小时内有效。如非本人操作，请忽略本条短信。 ";
	public static final String PHONE_PASSWORDRESET_CONTENT = "【数据道】验证码为：code。1个小时内有效。如非本人操作，请忽略本条短信。 ";
	public static final String PHONE_BIND_CONTENT = "【数据道】绑定手机验证码为：code。1个小时内有效。如非本人操作，请忽略本条短信。 ";
	
	public static final String SEND_REPLACE_CODE = "code";
	
	/**
	 * 网站应用：数据道
	 */
	public static final String WECHAT_WEBAPP_SHUJUDAO_APPID = "wx98f6e23d3e2f4373";
	public static final String WECHAT_WEBAPP_SHUJUDAO_SECRET = "8b90602bd5061963e1973b95e8fd81bb";
	
	 public static final String WECHAT_PULBIC_ACCOUNT_SHUJUDAOYINGYONG_APPID = "wx7233996558e4cdb9";
	  public static final String WECHAT_PULBIC_ACCOUNT_SHUJUDAOYINGYONG_SECRET = "de5625fc9b1c3d8a40cf55a9bd11e7a2";
	
	/**
	 * 微信公众平台
	 */
	public static final String PUBLIC_ACCOUNT_SHUJUDAO_APPID = "wx842aad37a02d49d5";
	public static final String PUBLIC_ACCOUNT_SHUJUDAO_SECRET = "7b3064c38bcf1474832d82a29c05ef0f";
	
	public static final String WECHAT_LOGINTYPE_WECHAT  = "wechat";
	public static final String WECHAT_LOGINTYPE_WEB  = "web";
	
	public static final Integer MESSAGE_RECORD_EMAIL = 0;
	public static final Integer MESSAGE_RECORD_SMS = 1;
}
