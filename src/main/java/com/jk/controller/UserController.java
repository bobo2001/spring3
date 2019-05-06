package com.jk.controller;

import com.jk.pojo.User;
import com.jk.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Controller
public class UserController {

    @Autowired
    private GoodService goodService;

    @RequestMapping("login")
    @ResponseBody
    public HashMap<String,Object> login(User userBean, String imgcode, HttpServletRequest request) {

        return goodService.login(userBean,imgcode,request);
    }

    //获取短信验证码
    @RequestMapping("gainMessgerCode")
    @ResponseBody
    public String gainMessgerCode(String phoneNumber, HttpSession session){

        return goodService.gainMessgerCode(phoneNumber,session);
    }
    //短信验证码登录
    @RequestMapping("messageLogin")
    @ResponseBody
    public String messageLogin(String account,String messageCode,HttpSession session){
        return goodService.messageLogin(account,messageCode,session);
    }

}
