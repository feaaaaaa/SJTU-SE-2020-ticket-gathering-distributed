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
@RequestMapping("/User")
public interface UserService {

    @RequestMapping("/Login")
    public Map<String,Object> login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password,
                                    HttpServletResponse response);

    @RequestMapping("/Register")
    public boolean register(@RequestBody User user);

    @RequestMapping("/ExistsByUsername")
    public boolean existsByUsername(@RequestParam(name = "username") String username);

    @RequestMapping("/FindByUserId")
    public User findUserByUserId(Integer userId);
}
