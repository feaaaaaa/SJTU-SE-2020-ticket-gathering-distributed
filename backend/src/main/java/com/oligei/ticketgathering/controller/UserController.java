/**
 * @ClassName User
 * @Description User Controller
 * @Author ziliuziliu
 * @Date 2020/7/10
 */

package com.oligei.ticketgathering.controller;

import com.oligei.ticketgathering.entity.mysql.User;
import com.oligei.ticketgathering.service.UserService;
import com.oligei.ticketgathering.util.TokenUtil;
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
        Map<String,Object> map = new HashMap<>();
        User existed_user = userService.login(username, password);
        if (existed_user == null) {
            map.put("message","login failure");
        }
        else {
            String token = TokenUtil.sign(existed_user);
            map.put("message","login success");
            map.put("token",token);
            map.put("user",existed_user);
        }
        System.out.println(map);
        return map;
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
