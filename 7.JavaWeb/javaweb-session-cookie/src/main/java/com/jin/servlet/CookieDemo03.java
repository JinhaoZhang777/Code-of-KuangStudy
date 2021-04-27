package com.jin.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

//中文数据传递
public class CookieDemo03 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解决中文乱码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8");

        //Cookie, 服务器端从客户端获取
        Cookie[] cookies = req.getCookies();   //从这里返回数组，说明Cookie可能存在多个

        PrintWriter out = resp.getWriter();
        //判断Cookie是否存在
        if (cookies!=null){
            //若果存在怎么办
            out.write("你上一次访问的时间是：");
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                //获取cookie的名字
                //解码
                if("name".equals(cookie.getName())){
                    out.write(URLDecoder.decode(cookie.getValue(),"utf8"));
                }

            }
        }else{
            out.write("这是你第一次访问本站");
        }

        //编码
        Cookie cookie = new Cookie("name", URLEncoder.encode("津", "utf-8"));
        resp.addCookie(cookie);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
