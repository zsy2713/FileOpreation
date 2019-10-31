package com.demo.fileoperation.controller;

import com.demo.fileoperation.domain.User;
import com.demo.fileoperation.service.UserService;
import com.demo.fileoperation.util.Info;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @RequestMapping("/index.html")
    public String index(HttpServletRequest request,ModelMap map){
        return "index";
    }


    @RequestMapping("/fileOp.html")
    public String fileOp(HttpServletRequest request,ModelMap map){
        map.put("user",request.getSession().getAttribute("user"));
        return "fileOp";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Info login(HttpServletRequest request){
        Map<String,String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        User u = userService.login(user);
        Info info = new Info();
        if (u != null){
            info.setFlag(true);
            request.getSession().setAttribute("user",u.getName());
        }else {
            info.setFlag(false);
            info.setMsg("账号或密码错误！");
        }
        return info;
    }

    @RequestMapping("/exit")
    public String exit(HttpServletRequest request){
        request.getSession().invalidate();
        return "fileOp";
    }

    @RequestMapping("/register.html")
    public String registerHtml(){
        return "register";
    }

    @RequestMapping("/register")
    @ResponseBody
    public Info register(HttpServletRequest request){
        Info info = new Info();
        User user = new User();
        Map<String,String[]> map = request.getParameterMap();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        boolean flag = userService.register(user);
        if (flag){
            info.setFlag(true);
            info.setMsg("注册成功");
        }else {
            info.setFlag(false);
            info.setMsg("注册失败");
        }
        return info;
    }
}
