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
     *@param username,password
     *@return Msg(status,msg,data) 200 login success, 201 login failure, 202 exception
     *@author ziliuziliu,Yang Yicheng
     *@date 2020/8/18
     */
    public Msg<Map<String,Object>> login(@RequestParam(name = "username") String username,
                                         @RequestParam(name = "password") String password) {
        Map<String,Object> map = new HashMap<>();
        User existed_user = null;
        try{
            existed_user = userService.login(username, password);
        }
        catch(NullPointerException e){
            logger.error("NullPointerException",e);
            return new Msg<>(202, "空参数", null);
        }
        catch (InvalidDataAccessApiUsageException e){
            logger.error("InvalidDataAccessApiUsageException",e);
            return new Msg<>(202,"错误的参数属性",null);
        }
        if (existed_user != null) {
            String token = TokenUtil.sign(existed_user);
            map.put("token", token);
            map.put("user", existed_user);
            return new Msg<>(200,"登录成功",map);
        }
        else
            return new Msg<>(201,"用户名或密码错误",null);
    }

    /**
     * register
     * @param user user to be registered
     * @return Msg(status,msg,data) 200 is OK, 201 is exception
     * @author ziliuziliu
     * @date 2020/8/18
     */
    @RequestMapping("/Register")
    public Msg<JSONObject> register(@RequestBody User user) {
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
     *@param username
     *@return Msg(status,msg,data) 200 username existed, 201 username not existed, 202 exception
     *@author ziliuziliu,Yang Yicheng
     *@date 2020/8/18
     */
    public Msg<Boolean> existsByUsername(@RequestParam(name = "username") String username) {
        try {
            boolean result = userService.existsByUsername(username);
            if (result) return new Msg<>(200,"用户名已存在",null);
            else return new Msg<>(201,"用户名不存在",null);
        }
        catch(NullPointerException e){
            logger.error("NullPointerException",e);
            return new Msg<>(202, "空参数", null);
        }
        catch (InvalidDataAccessApiUsageException e){
            logger.error("InvalidDataAccessApiUsageException",e);
            return new Msg<>(202,"错误的参数属性",null);
        }
    }

    @RequestMapping("/FindByUserId")
    /**
     *get userInfo by userId
     *@param userId
     *@return Msg(status,msg,data) 200 is exists, 201 is not exists, 202 is exception
     *@author ziliuziliu,Yang Yicheng
     *@date 2020/8/18
     *@throws NullPointerException if userId is not exist
     */
    public Msg<User> findUserByUserId(@RequestParam(name = "userId") Integer userId){
        try{
            User user = userService.findUserByUserId(userId);
            if (user != null) return new Msg<>(200,"用户编号存在",user);
            else return new Msg<>(201,"用户编号不存在",null);
        }
        catch(NullPointerException e){
            logger.error("NullPointerException",e);
            return new Msg<>(202, "空参数", null);
        }
        catch (InvalidDataAccessApiUsageException e){
            logger.error("InvalidDataAccessApiUsageException",e);
            return new Msg<>(202,"错误的参数属性",null);
        }
    }

    @RequestMapping("/rechargeOrDeduct")
    /**
    *recharge one's balance
    *@param: userid, increment
    *@return: com.oligei.ticketgathering.util.msgutils.Msg<java.lang.Integer>
    *@author: Cui Shaojie
    *@date: 2020/8/20
    */
    public Msg<Integer> rechargeOrDeduct(@RequestParam(name = "userId")Integer userId,@RequestParam(name = "increment")Integer increment){
        int result;
        try {
            result = userService.rechargeOrDeduct(userId,increment);
        }
        catch(NullPointerException e){
            logger.error("NullPointerException",e);
            return new Msg<>(202, "空参数", -2);
        }
        catch (InvalidDataAccessApiUsageException e){
            logger.error("InvalidDataAccessApiUsageException",e);
            return new Msg<>(202,"错误的参数属性",-3);
        }
        if(result == -1)
            return new Msg<>(201,"余额不能为负数",result);
        return new Msg<>(200,"充值或支付成功",result);
    }
}
