/**
 * @ClassName User
 * @Description User Controller
 * @Author ziliuziliu
 * @Date 2020/7/10
 */

package com.oligei.transferbackend.service;

import com.oligei.transferbackend.entity.User;
import com.oligei.transferbackend.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@FeignClient(value = "ticketGathering")
public interface UserService {

    @RequestMapping(value = "/User/Login",method = RequestMethod.GET)
    public Map<String,Object> login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password,
                      HttpServletResponse response);

    @RequestMapping(value = "/User/Register",method = RequestMethod.POST)
    public boolean register(@RequestBody User user);

    @RequestMapping(value = "/User/ExistsByUsername",method = RequestMethod.GET)
    public boolean existsByUsername(@RequestParam(name = "username") String username);

    @RequestMapping(value = "/User/FindByUserId",method = RequestMethod.GET)
    public User findUserByUserId(Integer userId);
}
