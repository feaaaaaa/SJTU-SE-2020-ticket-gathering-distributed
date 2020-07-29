/**
 * @ClassName User
 * @Description User Controller
 * @Author ziliuziliu
 * @Date 2020/7/10
 */

package com.oligei.transferbackend.controller;

import com.oligei.transferbackend.entity.User;
import com.oligei.transferbackend.service.UserService;
import com.oligei.transferbackend.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/User")
@CrossOrigin(origins = "*",maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/Login")
    public Map<String,Object> login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password,
                                    HttpServletResponse response) {

        return userService.login(username,password,response);
    }

    @RequestMapping("/Register")
    public boolean register(@RequestBody User user) {
        return userService.register(user);
    }

    @RequestMapping("/ExistsByUsername")
    public boolean existsByUsername(@RequestParam(name = "username") String username) {
        return userService.existsByUsername(username);
    }

    @RequestMapping("/FindByUserId")
    public User findUserByUserId(Integer userId){
        return userService.findUserByUserId(userId);
    }
}
