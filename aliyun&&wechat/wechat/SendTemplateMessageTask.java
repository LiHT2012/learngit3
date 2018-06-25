package com.backend.kfc.wechat;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.backend.kfc.util.JacksonUtil;
import com.backend.kfc.util.RestClient;
import com.backend.kfc.wechat.model.MiniProgramTemplateMessage;

public class SendTemplateMessageTask implements Runnable {

    private MiniProgramTemplateMessage message;
    private String url;
    

    public SendTemplateMessageTask() {
        super();
    }


    public SendTemplateMessageTask(MiniProgramTemplateMessage message, String url) {
        super();
        this.message = message;
        this.url = url;
    }


    @Override
    public void run() {
        if (StringUtils.isAnyBlank(url,message.getTouser()) || null == message) {
            return;
        }
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println(JacksonUtil.obj2Json(this.message));
        @SuppressWarnings("unchecked")
        Map<String,Object> map = RestClient.postForEntity(url, JacksonUtil.obj2Json(message), Map.class);
        Integer errcode = (Integer)map.get("errcode");
        System.out.println(JacksonUtil.obj2Json(map));
        if (Integer.valueOf(0).equals(errcode)) {
            return;
        }
    }

}
