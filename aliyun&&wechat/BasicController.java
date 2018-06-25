package com.backend.kfc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.backend.kfc.cfg.SystemUrlCfg;
import com.backend.kfc.model.mysql.OilUser;

@Controller
public class BasicController {
    
    @Autowired
    protected HttpServletRequest request;
    
    protected OilUser getOilUserFromCurrentSession() {
        HttpSession session = request.getSession();
        return (OilUser) session.getAttribute(SystemUrlCfg.LOGIN_USER);
    }
    
}
