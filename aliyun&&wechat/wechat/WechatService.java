package com.backend.kfc.wechat;

import java.io.InputStream;

import com.backend.kfc.exception.GeneralException;
import com.backend.kfc.wechat.model.MiniProgramTemplateMessage;

public interface WechatService {

    /**
     * 给所有用户发消息
     * @param notification
     * @return
     * @throws GeneralException
     */
    void sendTemplateMessageToWechatUsers(MiniProgramTemplateMessage message)
            throws GeneralException;
    String getOpenId(String jsCode) throws GeneralException;

    InputStream getQRInputStream(String sceneParam) throws GeneralException;

    

}
