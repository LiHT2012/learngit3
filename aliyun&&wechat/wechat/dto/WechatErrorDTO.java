package com.backend.kfc.wechat.dto;

public class WechatErrorDTO {
    private Integer errcode;
    private String errmsg;
    public Integer getErrcode() {
        return errcode;
    }
    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }
    public String getErrmsg() {
        return errmsg;
    }
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
    
}
