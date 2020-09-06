package com.oligei.gateway.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.oligei.gateway.util.TokenUtil;
import com.oligei.gateway.util.msgutils.Msg;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURL());
        String token = request.getHeader("token");
        System.out.println(token);
        if (token != null && !token.equals("null")) {
            boolean result = TokenUtil.authenverify(token);
            System.out.println(result);
            if (result) {
                System.out.println("authentication passed");
                return true;
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            JSONObject json = new JSONObject();
//            json.put("message","authentication failure");
            json.put("msg", "认证失败！");
            json.put("status", -100);
            json.put("data", null);
            response.getWriter().append(json.toJSONString());
            System.out.println("authentication failure");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
            return false;
        }
        return false;
    }
}
