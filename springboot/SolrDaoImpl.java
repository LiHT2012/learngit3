package com.dbcool.api.action.solr.dao;

import java.util.Collection;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dbcool.api.cfg.EnvConfigBean;

/**
 * solr接口的实现，对增删改查的具体实现
 * 
 * @author wangjm
 *
 */
@Repository
public abstract class SolrDaoImpl implements SolrDao {
	
	@Autowired
    private EnvConfigBean envCfgBean;
	
	private  String zk_host = null;
    private  CloudSolrClient cloudSolrClient = null;

	void init(String solrCore) {
		zk_host = envCfgBean.getZkHost();
		cloudSolrClient = new CloudSolrClient.Builder().withZkHost(zk_host).build();
    	cloudSolrClient.setDefaultCollection(solrCore);
    	cloudSolrClient.connect();
	}
    
    @Override
    public void insertOneToCore(Object obj) {
        try {
            cloudSolrClient.addBean(obj);
            cloudSolrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertListToCore(List<? extends Object> objs) {
        try {
            cloudSolrClient.addBeans(objs);
            cloudSolrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertDocumentToCore(SolrInputDocument document) {
        try {
            cloudSolrClient.add(document);
            cloudSolrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertDocumentsToCore(Collection<SolrInputDocument> documents) {
        try {
            cloudSolrClient.add(documents);
            cloudSolrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOneToCore(Object obj) {
        try {
            cloudSolrClient.addBean(obj);
            cloudSolrClient.commit();
        } catch (Exception e) {
        }
    }

    @Override
    public void updateListToCore(List<? extends Object> objs) {
        try {
            cloudSolrClient.addBeans(objs);
            cloudSolrClient.commit();
        } catch (Exception e) {
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            cloudSolrClient.deleteById(id);
            cloudSolrClient.commit();
        } catch (Exception e) {
        }
    }

    @Override
    public void deleteByIds(List<String> ids) {
        try {
            cloudSolrClient.deleteById(ids);
            cloudSolrClient.commit();
        } catch (Exception e) {
        }
    }

    @Override
    public void deleteByCondition(String query) {
        try {
            cloudSolrClient.deleteByQuery(query);
            cloudSolrClient.commit();
        } catch (Exception e) {
        }
    }

    @Override
    public void deleteAll() {
        try {
            cloudSolrClient.deleteByQuery("*:*");
            cloudSolrClient.commit();
        } catch (Exception e) {
        }
    }

    @Override
    public SolrDocument selectById(String id) {
        SolrDocument document = null;
        try {
            document = cloudSolrClient.getById(id);
            return document;
        } catch (Exception e) {
        }
        return document;
    }

    @Override
    public SolrDocumentList selectById(Collection<String> ids) {
        SolrDocumentList document = null;
        try {
            document = cloudSolrClient.getById(ids);
        } catch (Exception e) {
        }
        return document;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> selectByCondition(SolrQuery solrQuery, Class<?> T) {
        List<T> list = null;
        QueryResponse response = null;
        try {
            response = cloudSolrClient.query(solrQuery);
            if (null == response) {
                return null;
            }
            list = (List<T>) response.getBeans(T);
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    @Override
    public SolrDocumentList selectByCondition(SolrQuery solrQuery) {
        QueryResponse response = null;
        SolrDocumentList documentList = null;
        try {
            response = cloudSolrClient.query(solrQuery);
            documentList = response.getResults();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return documentList;
    }

    @Override
    public QueryResponse selectByConditionReturnResponse(SolrQuery solrQuery) {
        QueryResponse response = null;
        try {
            response = cloudSolrClient.query(solrQuery);
        } catch (Exception e) {
        }
        return response;
    }
}
