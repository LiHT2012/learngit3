package com.dbcool.api.cfg;


public class SystemUrlCfg {

	/**
	 * 用户对象session字段
	 */
	public static final String  LOGIN_USER = "loginuser";
	public static final String  LOGIN_ULISTS = "ulists";
	/**
	 * url路径session字段
	 */
	public static final String  RETURN_URL = "returnURL";
	
	/**
	 * 经常修改的配置(config.properties)
	 */
//	public static final String CONFIG = "config";
	public static final String CONFIG = "configure";
	
	public static final String APPLICATION = "application";
	
	/**
	 * 公开放行地址(anonymousActions.properties)
	 */
	public static final String ANONYMOUS_ACTIONS = "anonymousActions";
	/**
	 * 公开放行地址(logintopage.properties)
	 */
	public static final String LOGIN_TO_PAGE = "logintopage";
	/**
	 * 信息内容(messages.properties)
	 */
	public static final String MESSAGE = "messages";
	/**
	 * 一般错误提示页面,该路径需要匹配页面前后缀
	 */
	public static final String ERROR_PAGE = "/error/error_new";
	

  /**
   * solr服务器ip地址
   */
  private static String SOLR_HOST;

  public static String getSolrHost() {
    return SOLR_HOST;
  }

}
