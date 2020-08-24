/**
 * @ClassName User
 * @Description User Controller
 * @Author ziliuziliu
 * @Date 2020/7/10
 */

package com.oligei.gateway.service;

import com.oligei.gateway.entity.User;
import com.oligei.gateway.util.msgutils.Msg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@FeignClient(value = "ticketgathering")
public interface UserService {

    @RequestMapping(value = "/User/Login",method = RequestMethod.GET)
    public Msg<Map<String,Object>> login(@RequestParam(name = "username") String username,
                                         @RequestParam(name = "password") String password);

    @RequestMapping(value = "/User/Register",method = RequestMethod.POST)
    public Msg<Boolean> register(@RequestBody User user);

    @RequestMapping(value = "/User/ExistsByUsername",method = RequestMethod.GET)
    public Msg<Boolean> existsByUsername(@RequestParam(name = "username") String username);

    @RequestMapping(value = "/User/FindByUserId",method = RequestMethod.GET)
    public Msg<User> findUserByUserId(@RequestParam(name = "userId")Integer userId);

    @RequestMapping(value = "/User/rechargeOrDeduct",method = RequestMethod.POST)
    public Msg<Integer> rechargeOrDeduct(@RequestParam(name = "userId")Integer userId,@RequestParam(name = "increment")Integer increment);
    }
