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
    /**
     *check username and password
     *@Param [username, password]
     *@return com.oligei.ticketgathering.entity.mysql.User
     *@Author Yang Yicheng
     *@date 2020/8/18
     */
    public Msg<Map<String,Object>> login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password,
                                         HttpServletResponse response) {
        Map<String,Object> map = new HashMap<>();
        User existed_user=null;
        try{
            existed_user= userService.login(username, password);
        }
        catch(NullPointerException e){
            logger.error("NullPointerException",e);
            map.put("message","login failure");
            return new Msg<Map<String,Object>>(201,"密码错误或用户不存在",map);
        }
//        if (existed_user == null) {
//            map.put("message","login failure");
//        }
//        else {
        String token = TokenUtil.sign(existed_user);
        map.put("message","login success");
        map.put("token",token);
        map.put("user",existed_user);


//        }
//        System.out.println(map);
        return new Msg<Map<String,Object>>(200,"登录成功" ,map);
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
    /**
     *check whether the user is exsisted
     *@Param [username]
     *@return boolean
     *@Author Yang Yicheng
     *@date 2020/8/18
     */
    public Msg<Boolean> existsByUsername(@RequestParam(name = "username") String username) {
        return new Msg<Boolean>(200,"查询成功",userService.existsByUsername(username));
    }

    @RequestMapping("/FindByUserId")
    /**
     *get userInfo by userId
     *@Param [userId]
     *@return com.oligei.ticketgathering.entity.mysql.User
     *@Author Yang Yicheng
     *@date 2020/8/18
     *@Throws NullPointerException if userId is not exist
     */
    public Msg<User> findUserByUserId(Integer userId){
        User user=null;
        try{
            user=userService.findUserByUserId(userId);
        }
        catch(NullPointerException e){
            logger.error("NullPointerException",e);
            return new Msg<User>(201,"该用户不存在",null);
        }
        return new Msg<User>(201,"查询成功",user);
    }
}
