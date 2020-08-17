/**
 * @ClassName User
 * @Description User Controller
 * @Author ziliuziliu
 * @Date 2020/7/10
 */

package com.oligei.ticketgathering.controller;
import com.oligei.ticketgathering.TicketGatheringApplication;
import com.oligei.ticketgathering.entity.mysql.User;
import com.oligei.ticketgathering.service.UserService;
import com.oligei.ticketgathering.util.TokenUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import com.oligei.ticketgathering.util.msgutils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/User")
@CrossOrigin(origins = "*",maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(TicketGatheringApplication.class);

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

    /**
     * register
     * @param user user to be registered
     * @return Msg(status,msg,data) 200 is OK, 201 is fail
     * @author
     * @date
     */
    @RequestMapping("/Register")
    public Msg<JSONObject> register(@RequestBody User user) {
        if(user==null){
            logger.error("NullPointerException",new NullPointerException("null user --UserController register"));
            return MsgUtil.makeMsg(201,"空参数",null);
        }
        try {
            userService.register(user);
        }catch (NullPointerException e){
            logger.error("NullPointerException",e);
            return MsgUtil.makeMsg(201,"空参数",null);
        }catch (InvalidDataAccessApiUsageException e){
            logger.error("InvalidDataAccessApiUsageException",e);
            return MsgUtil.makeMsg(201,"错误的参数属性",null);
        }
        return MsgUtil.makeMsg(200,"注册成功",null);
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
