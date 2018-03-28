package com.dbcool.api.cfg;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvConfigBean {
	
	private static final String ENV_PROP_ACTIVE_PROFILE = "spring.profiles.active";
	private static final String DBC_URL_HOST = "dbc_url";
    private static final String PUBLIC_USER_URL_HOST = "public_user_url";
    private static final String WECHAT_PUBLIC_PLATFORM_APPID = "wechat_public_platform_appId";
    private static final String WECHAT_WEBAPP_APPID = "wechat_webApp_appId";
    private static final String ZK_HOST = "zk_host";
    private static final String SOLR_CORE_TYPE_DOC = "solr_core_factube_doc";
    private static final String SOLR_CORE_TYPE_DBC = "solr_core_factube_dbc";
    private static final String SOLR_CORE_TYPE_ROLE = "solr_core_factube_role";
    private static final String SOLR_CORE_TYPE_ADDRESS = "solr_core_address";
    
    
	@Autowired
	private Environment env;
	
	private String runTimeEnv;
	private String dbcUrlHost;
	private String publicUserUrlHost;
	private String wechatPublicPlatFormAppId;
    private String wechatWebAppAppId;
    private String zkHost;
    private String solrCoreDbc;
    private String solrCoreDoc;
    private String solrCoreRole;
    private String solrCoreAddress;
    
	
	@PostConstruct
	void init() {
		runTimeEnv = env.getProperty(ENV_PROP_ACTIVE_PROFILE);
		dbcUrlHost = env.getProperty(DBC_URL_HOST) + "/dbc/";
		zkHost = env.getProperty(ZK_HOST);
		wechatPublicPlatFormAppId = env.getProperty(WECHAT_PUBLIC_PLATFORM_APPID);
		wechatWebAppAppId = env.getProperty(WECHAT_WEBAPP_APPID);
		publicUserUrlHost = env.getProperty(PUBLIC_USER_URL_HOST) + "/factube/";
		solrCoreDbc = env.getProperty(SOLR_CORE_TYPE_DBC);
		solrCoreDoc = env.getProperty(SOLR_CORE_TYPE_DOC);
		solrCoreRole = env.getProperty(SOLR_CORE_TYPE_ROLE);
		solrCoreAddress = env.getProperty(SOLR_CORE_TYPE_ADDRESS);
	}

	public String getRunTimeEnv() {
		return runTimeEnv;
	}

	public void setRunTimeEnv(String runTimeEnv) {
		this.runTimeEnv = runTimeEnv;
	}

	public String getDbcUrlHost() {
		return dbcUrlHost;
	}

	public void setDbcUrlHost(String dbcUrlHost) {
		this.dbcUrlHost = dbcUrlHost;
	}

	public String getWechatPublicPlatFormAppId() {
        return wechatPublicPlatFormAppId;
    }

    public void setWechatPublicPlatFormAppId(String wechatPublicPlatFormAppId) {
        this.wechatPublicPlatFormAppId = wechatPublicPlatFormAppId;
    }

    public String getWechatWebAppAppId() {
        return wechatWebAppAppId;
    }

    public void setWechatWebAppAppId(String wechatWebAppAppId) {
        this.wechatWebAppAppId = wechatWebAppAppId;
    }

    public String getPublicUserUrlHost() {
        return publicUserUrlHost;
    }

    public void setPublicUserUrlHost(String publicUserUrlHost) {
        this.publicUserUrlHost = publicUserUrlHost;
    }

	public String getZkHost() {
		return zkHost;
	}

	public void setZkHost(String zkHost) {
		this.zkHost = zkHost;
	}

	public String getSolrCoreDbc() {
		return solrCoreDbc;
	}

	public void setSolrCoreDbc(String solrCoreDbc) {
		this.solrCoreDbc = solrCoreDbc;
	}

	public String getSolrCoreDoc() {
		return solrCoreDoc;
	}

	public void setSolrCoreDoc(String solrCoreDoc) {
		this.solrCoreDoc = solrCoreDoc;
	}

	public String getSolrCoreAddress() {
		return solrCoreAddress;
	}

	public void setSolrCoreAddress(String solrCoreAddress) {
		this.solrCoreAddress = solrCoreAddress;
	}

	public String getSolrCoreRole() {
		return solrCoreRole;
	}

	public void setSolrCoreRole(String solrCoreRole) {
		this.solrCoreRole = solrCoreRole;
	}
}
