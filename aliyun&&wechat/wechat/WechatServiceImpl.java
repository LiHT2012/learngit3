package com.backend.kfc.wechat;

import static com.backend.kfc.wechat.WechatConstants.*;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.kfc.exception.GeneralException;
import com.backend.kfc.util.TwoTuple;
import com.backend.kfc.wechat.model.MiniProgramTemplateMessage;

@Service
public class WechatServiceImpl implements WechatService {
    private static final String MINIPROGRAM_QR_PAGE = "pages/qrConfirm/qrConfirm";
    @Autowired
    private WechatComponet wechatComponet;

    @Override
    public void sendTemplateMessageToWechatUsers(MiniProgramTemplateMessage message) throws GeneralException {
//        if (StringUtils.isNoneBlank(message.getTouser(), message.getFrom_id())) {
//            String accessToken = getKfcAccessToken();
//            wechatComponet.sendTemplateMessageAsMiniProgram(accessToken, message);
//        }
        return ;
    }

    @Override
    public String getOpenId(String jsCode) throws GeneralException {
        TwoTuple<String, String> result = wechatComponet.miniProgramGetSessionKeyAndOpenIdByCode(jsCode, APPID_MINIPROGRAM_KFC, SECRETKEY_MINIPROGRAM_KFC);
        if (null != result) {
            return result.getFirst();
        }
        return null;
    }
    
    @Override
    public InputStream getQRInputStream(String billId) throws GeneralException {
        String accessToken = getKfcAccessToken();
        return wechatComponet.getminiqrQr(accessToken, billId, MINIPROGRAM_QR_PAGE);
    }
    
    private String getKfcAccessToken() throws GeneralException {
        String accessToken = wechatComponet.getAccessTokenWithAppIdAndSecretKey(APPID_MINIPROGRAM_KFC, SECRETKEY_MINIPROGRAM_KFC);
        if (StringUtils.isBlank(accessToken)) {
            throw new GeneralException("get accesstoken failed");
        }
        return accessToken;
    }
}
