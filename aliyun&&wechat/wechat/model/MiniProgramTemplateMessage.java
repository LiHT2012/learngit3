package com.backend.kfc.wechat.model;

import java.util.Map;

public class MiniProgramTemplateMessage {
    public static class TemplateMsgKeywordData{
        private String value;
        
        public TemplateMsgKeywordData() {
            super();
        }
        public TemplateMsgKeywordData(String value) {
            super();
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
    }
    private String template_id;
    private String page;//点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
    private String from_id;//表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
    private Map<String,TemplateMsgKeywordData> data;
    private String emphasis_keyword;   //模板需要放大的关键词，不填则默认无放大
    private String touser;
    
    public MiniProgramTemplateMessage() {
        super();
    }
    public MiniProgramTemplateMessage(String template_id, String page, String from_id,
            Map<String, TemplateMsgKeywordData> data, String touser) {
        super();
        this.template_id = template_id;
        this.page = page;
        this.from_id = from_id;
        this.data = data;
        this.touser = touser;
    }
    public String getTemplate_id() {
        return template_id;
    }
    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }
    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }
    public String getFrom_id() {
        return from_id;
    }
    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }
    public Map<String, TemplateMsgKeywordData> getData() {
        return data;
    }
    public void setData(Map<String, TemplateMsgKeywordData> data) {
        this.data = data;
    }
    public String getEmphasis_keyword() {
        return emphasis_keyword;
    }
    public void setEmphasis_keyword(String emphasis_keyword) {
        this.emphasis_keyword = emphasis_keyword;
    }
    public String getTouser() {
        return touser;
    }
    public void setTouser(String touser) {
        this.touser = touser;
    }
    
}
