package com.backend.kfc.wechat.dto;

import java.io.Serializable;
import java.util.List;

public class WechatAllOpenIdsDTO extends WechatErrorDTO implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -7837795685739404839L;
    private Integer total;
    private Integer count;
    private String next_openid;
    private OpenIdList data;
    
    public class OpenIdList {
        private List<String> openid;

        public List<String> getOpenid() {
            return openid;
        }

        public void setOpenid(List<String> openId) {
            this.openid = openId;
        }
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNext_openid() {
        return next_openid;
    }

    public void setNext_openid(String next_openid) {
        this.next_openid = next_openid;
    }

    public OpenIdList getData() {
        return data;
    }

    public void setData(OpenIdList data) {
        this.data = data;
    }
}
