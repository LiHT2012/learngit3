=== 微信登录

1. 使用微信jsapi不仅要绑定已备案的域名，而且还需要一大堆接口注入权限验证
2. jsapi_ticket是公众号用于调用微信JS接口的临时票据。正常情况下，jsapi_ticket的有效期为7200秒，通过access_token来获取。
由于获取jsapi_ticket的api调用次数非常有限，频繁刷新jsapi_ticket会导致api调用受限，影响自身业务，开发者必须在自己的服务全局缓存jsapi_ticket


//小程序通过临时code获取sessionKey及openId
public TwoTuple<String, String> miniProgramGetSessionKeyAndOpenIdByCode(String jsCode, String appId, String appSecret) throws GeneralException{
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
        throw new GeneralException("get openId failed, errcode : " + result.getErrcode() + " errmsg : " + result.getErrmsg());
    }
    return ret;
}

@Component
public class SendTemplateThreadManager {

  private static int CORE_POOL_SIZE = 5;
  private static int MAX_POOL_SIZE = 10;
  private static long KEEPALIVE_TIME = 60L;//seconds
  private static int WORK_QUEUE_LENGTH = 10000;
  private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(WORK_QUEUE_LENGTH);

  private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
          KEEPALIVE_TIME, TimeUnit.SECONDS, workQueue);
  @PreDestroy
  private void destory() {
      threadPool.shutdown();
  }

  public void sendMessage(MiniProgramTemplateMessage message, String url) {
    threadPool.execute(new SendTemplateMessageTask(message, url));
}
//常量注释
/**
public static final String WECHAT_REPLACE_APPID = "APPID";
    public static final String WECHAT_REPLACE_APPSECERT = "APPSECRET";
    public static final String WECHAT_REPLACE_SECRET = "SECRET";
    public static final String WECHAT_REPLACE_CODE = "CODE";
    public static final String WECHAT_REPLACE_JSCODE = "JSCODE";

    public static final String WECHAT_REPLACE_ACCESSTOKEN = "ACCESS_TOKEN";
    public static final String WECHAT_REPLACE_OPENID = "OPENID";
    public static final String WECHAT_REPLACE_NEXT_OPENID = "NEXT_OPENID";

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
*/
