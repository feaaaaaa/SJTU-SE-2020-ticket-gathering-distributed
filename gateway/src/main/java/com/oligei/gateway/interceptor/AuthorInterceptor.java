package com.oligei.gateway.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.oligei.gateway.util.TokenUtil;
import com.oligei.gateway.util.msgutils.Msg;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthorInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (!token.equals("null")) {
            boolean result = TokenUtil.adminverify(token);
            if (result) {
                System.out.println("authorization passed");
                return true;
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            JSONObject json = new JSONObject();
            json.put("msg", "无权限，请重新认证！");
            json.put("status", -101);
            json.put("data", null);
            response.getWriter().append(json.toJSONString());
            System.out.println("authorization failure");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
            return false;
        }
        return false;
    }
}
