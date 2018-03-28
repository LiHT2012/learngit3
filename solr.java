public static SolrQuery getPointAddressQuery(String address){
  SolrQuery params = new SolrQuery();
  String q_params = "station_address:"+address;
  params.set("q", q_params); params.set("fq", "{!geofilt}"); //距离过滤函数
  //params.set("fq","{!bbox}"); //距离过滤函数：圆的外接矩形
  params.set("pt", "118.227985 39.410722"); //当前经纬度
  params.set("sfield", "station_position"); //经纬度的字段
  params.set("d", "50"); //就近 d km的所有数据 //
  params.set("score", "distance");
  params.set("sort", "geodist() asc"); //根据距离排序：由近到远
  params.set("start", "0"); //记录开始位置
  params.set("rows", "100"); //查询的行数
  params.set("fl", "*,_dist_:geodist(),score");
  return params; }
链接：https://www.codercto.com/a/5307.html

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
  }
//
  @Repository("docDao")
public class SolrDocDaoImpl extends SolrDaoImpl {

	@Autowired
	private EnvConfigBean env;

	@PostConstruct
	void init() {
		super.init(env.getSolrCoreDoc());
	}
}
//调用
@Service
public class DocCoreServiceImpl implements DocCoreService {
	@Autowired
	@Qualifier("docDao")
	private SolrDao solrDao;
  //……此处调用的指定了docCore
}
