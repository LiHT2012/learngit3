package com.backend.kfc.wechat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.backend.kfc.wechat.model.MiniProgramTemplateMessage;

/**
 * 微信小程序模板消息的工厂类
 * @author wxy
 *
 */
public class BillTemplateMessageFactory {
    
    private static final String TEMPLATE_ID_UPDATE_BILL = "d7-H0pocbtDKHYJZC7_Ty89XwHx-oXvvaynQcPio38s";
    
    private static final String PAGE_ADD_BILL = "pages/oilApplyMsg/oilApplyMsg?msgId=";
    private static final String PAGE_EDIT_BILL = "pages/updateBillMsg/updateBillMsg?msgId=";
    private static final String PAGE_DELETE_BILL = "pages/deleteBillMsg/deleteBillMsg?msgId=";
    private static final String PAGE_BACKEND_BILL = "pages/returnBillMsg/returnBillMsg?msgId=";
    
    private static final String TEMPLATE_KEY_1 = "keyword1";
    private static final String TEMPLATE_KEY_2 = "keyword2";
    private static final String TEMPLATE_KEY_3 = "keyword3";
    private static final String TEMPLATE_KEY_4 = "keyword4";
    
    private static final String EDIT_BILL_STATUS_KEYWORD_VALUE = "订单修改待确认";
    private static final String DELETE_BILL_STATUS_KEYWORD_VALUE = "订单删除待确认";
    private static final String ADD_BILL_STATUS_KEYWORD_VALUE = "";
    private static final String BACKEND_BILL_STATUS_KEYWORD_VALUE = "订单已返单";
    
    
    private static final String WARM_PROMPT = "如已完成可忽略此消息";
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA);
    
    private static MiniProgramTemplateMessage createDefaultMessageOfTempleteUpdateBill(String formId, String openId, String page,
            String billStatus, String statement) {
        MiniProgramTemplateMessage.TemplateMsgKeywordData innerData = null;
        Map<String, MiniProgramTemplateMessage.TemplateMsgKeywordData> data = new HashMap<>();
        //keyword1 : 订单状态文字
        innerData = new MiniProgramTemplateMessage.TemplateMsgKeywordData(billStatus);
        data.put(TEMPLATE_KEY_1, innerData);
        
      //keyword2 : 订单修改时间
        innerData = new MiniProgramTemplateMessage.TemplateMsgKeywordData(DATE_FORMAT.format(new Date()));
        data.put(TEMPLATE_KEY_2, innerData);
        
      //keyword3 : 说明
        innerData = new MiniProgramTemplateMessage.TemplateMsgKeywordData(statement);
        data.put(TEMPLATE_KEY_3, innerData);
        
      //keyword4 : 温馨提示
        innerData = new MiniProgramTemplateMessage.TemplateMsgKeywordData(WARM_PROMPT);
        data.put(TEMPLATE_KEY_4, innerData);

        return new MiniProgramTemplateMessage(TEMPLATE_ID_UPDATE_BILL, page, formId, data, openId);
    }

    public static MiniProgramTemplateMessage createEditBillMessage(String formId, String openId, String operatorCompanyName, String billId,  String messageId) {
        StringBuffer buffer = new StringBuffer();
        String page = buffer.append(PAGE_EDIT_BILL).append(messageId).toString();
        buffer.delete(0, buffer.length());
        String statement = buffer.append(operatorCompanyName).append(" 发起了 修改单据").append(" \"").append(billId).append("\" ")
                .append("的申请, 请及时处理").toString();
        buffer = null;
        return createDefaultMessageOfTempleteUpdateBill(formId, openId, page , EDIT_BILL_STATUS_KEYWORD_VALUE, statement);
    }
    public static MiniProgramTemplateMessage createDeleteBillMessage(String formId, String openId, String operatorCompanyName, String billId,  String messageId) {
        StringBuffer buffer = new StringBuffer();
        String page = buffer.append(PAGE_DELETE_BILL).append(messageId).toString();
        buffer.delete(0, buffer.length());
        String statement = buffer.append(operatorCompanyName).append(" 发起了 删除单据").append(" \"").append(billId).append("\" ")
                .append("的申请, 请及时处理").toString();
        buffer = null;
        return createDefaultMessageOfTempleteUpdateBill(formId, openId, page , DELETE_BILL_STATUS_KEYWORD_VALUE , statement);
    }
    public static MiniProgramTemplateMessage createAddBillMessage() {
        return new MiniProgramTemplateMessage();
    }
    public static MiniProgramTemplateMessage createBackendMessage(String formId, String openId, String operatorCompanyName, String billId,  String messageId) {
        StringBuffer buffer = new StringBuffer();
        String page = buffer.append(PAGE_BACKEND_BILL).append(messageId).toString();
        buffer.delete(0, buffer.length());
        String statement = buffer.append(operatorCompanyName).append(" 发起了 单据").append(" \"").append(billId).append("\" ")
                .append("的返单, 请及时处理").toString();
        buffer = null;
        return createDefaultMessageOfTempleteUpdateBill(formId, openId, page , BACKEND_BILL_STATUS_KEYWORD_VALUE , statement);
    }
}
