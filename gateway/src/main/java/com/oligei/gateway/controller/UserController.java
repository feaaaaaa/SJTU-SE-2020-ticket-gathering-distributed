/**
 * @ClassName User
 * @Description User Controller
 * @Author ziliuziliu
 * @Date 2020/7/10
 */

package com.oligei.gateway.controller;

import com.oligei.gateway.GatewayApplication;
import com.oligei.gateway.entity.User;
import com.oligei.gateway.service.UserService;
import com.oligei.gateway.util.msgutils.Msg;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(GatewayApplication.class);

    @RequestMapping("/Login")
    public Msg<Map<String, Object>> login(@RequestParam(name = "username") String username,
                                          @RequestParam(name = "password") String password) {
        try {
            return userService.login(username, password);
        } catch (feign.RetryableException e) {
            logger.error("请求超时", e);
            return new Msg<>(504, "请求超时，请重试", null);
        } catch (FeignException.InternalServerError e) {
            logger.error("出错了", e);
            return new Msg<>(500, "出错了", null);
        }
    }

    @RequestMapping("/Register")
    public Msg<Boolean> register(@RequestBody User user) {
        try {
            return userService.register(user);
        } catch (feign.RetryableException e) {
            logger.error("请求超时", e);
            return new Msg<>(504, "请求超时，请重试", null);
        } catch (FeignException.InternalServerError e) {
            logger.error("出错了", e);
            return new Msg<>(500, "出错了", null);
        }
    }

    @RequestMapping("/ExistsByUsername")
    public Msg<Boolean> existsByUsername(@RequestParam(name = "username") String username) {
        try {
            return userService.existsByUsername(username);
        } catch (feign.RetryableException e) {
            logger.error("请求超时", e);
            return new Msg<>(504, "请求超时，请重试", null);
        } catch (FeignException.InternalServerError e) {
            logger.error("出错了", e);
            return new Msg<>(500, "出错了", null);
        }
    }

    @RequestMapping("/FindByUserId")
    public Msg<User> findUserByUserId(@RequestParam(name = "userId") Integer userId) {
        try {
            return userService.findUserByUserId(userId);
        } catch (feign.RetryableException e) {
            logger.error("请求超时", e);
            return new Msg<>(504, "请求超时，请重试", null);
        } catch (FeignException.InternalServerError e) {
            logger.error("出错了", e);
            return new Msg<>(500, "出错了", null);
        }
    }

    @RequestMapping("/rechargeOrDeduct")
    public Msg<Integer> rechargeOrDeduct(@RequestParam(name = "userId") Integer userId, @RequestParam(name = "increment") Integer increment) {
        try {
            return userService.rechargeOrDeduct(userId, increment);
        } catch (feign.RetryableException e) {
            logger.error("请求超时", e);
            return new Msg<>(504, "请求超时，请重试", null);
        } catch (FeignException.InternalServerError e) {
            logger.error("出错了", e);
            return new Msg<>(500, "出错了", null);
        }
    }
}
