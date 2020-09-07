/**
 * @ClassName User
 * @Description User Service Implementation
 * @Author ziliuziliu
 * @Date 2020/7/9
 */

package com.oligei.ticketgathering.serviceimpl;

import com.oligei.ticketgathering.dao.UserDao;
import com.oligei.ticketgathering.entity.mysql.User;
import com.oligei.ticketgathering.service.UserService;
import com.oligei.ticketgathering.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@EnableScheduling
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    /**
     * @param username username which cannot be null
     * @param password
     * @return the saved user or null
     * @author ziliuziliu, Yang Yicheng
     * @date 2020/8/18
     * @throws NullPointerException username or password is null
     */
    public User login(String username, String password) {
        Objects.requireNonNull(username, "null username --UserServiceImpl login");
        Objects.requireNonNull(password, "null password --UserServiceImpl login");
        return userDao.login(username,password);
    }

    @Override
    /**
     * @param user  user to be registered
     * @return register success or fail
     * @author ziliuziliu, feaaaaaa
     * @date 2020/8/18
     * @throws NullPointerException if user is null
     */
    public boolean register(User user) {
        Objects.requireNonNull(user, "null user --UserServiceImpl register");
        user.setBalance(500);
        return userDao.register(user);
    }

    @Override
    /**
     * @param username
     * @return true if exists, false if not
     * @Author ziliuziliu, Yang Yicheng
     * @date 2020/8/18
     * @throws NullPointerException username null
     */
    public boolean existsByUsername(String username) {
        Objects.requireNonNull(username, "null username --UserServiceImpl existsByUsername");
        return userDao.existsByUsername(username);
    }

    @Override
    /**
     * @param userId
     * @return User or null
     * @author ziliuziliu, Yang Yicheng
     * @date 2020/8/18
     * @throws NullPointerException userId null
     */
    public User findUserByUserId(Integer userId) {
        Objects.requireNonNull(userId, "null userId --UserServiceImpl findUserByUserId");
        String cacheName = "User"+userId.toString();
        User user = (User) redisUtil.lGetIndex(cacheName,0);
        if (user == null) {
            user = userDao.findUserByUserId(userId);
            if (user == null) return null;
            redisUtil.lSet("UserList", user.getUserId());
            redisUtil.lSet(cacheName, user);
        }
        return user;
    }

    @Override
    /**
     * @param increment
     * @param increment
     * @return java.lang.Integer
     * @author Cui Shaojie
     * @date: 2020/8/20
     * @throws NullPointerException userid null
     */
    public Integer rechargeOrDeduct(Integer userid, Integer increment) {
        Objects.requireNonNull(userid, "null userid --UserServiceImpl rechargeOrDeduct");
        Objects.requireNonNull(increment, "null increment --UserServiceImpl rechargeOrDeduct");

        Boolean inRedis = false;
        String cacheName = "User"+userid.toString();
        User user = (User) redisUtil.lGetIndex(cacheName,0);
        if (user == null) {
            user = userDao.findUserByUserId(userid);
            redisUtil.lSet("UserList",user.getUserId());
        }
        else inRedis = true;
        int result = user.getBalance() + increment;
        if (result < 0) return -1;
        user.setBalance(result);
        if (inRedis) redisUtil.lUpdateIndex(cacheName,0,user);
        else redisUtil.lSet(cacheName,user);
        return result;
    }

    @Override
    @Scheduled(cron = "0 */15 * * * ? ")
    /**
     * flush user info back to databse
     * @author ziliuziliu
     * @date 2020/9/6
     */
    public Boolean flushUser() {
        System.out.println("Here to flush users.");
        List<Object> userList = redisUtil.lGet("UserList",0,-1);
        if (userList == null) return true;
        for (Object object:userList) {
            Integer userid = (Integer) object;
            User user = (User) redisUtil.lGetIndex("User"+userid.toString(),0);
            userDao.save(user);
            redisUtil.lLeftPop("User"+userid.toString());
        }
        while (redisUtil.lGetListSize("UserList") > 0)
            redisUtil.lLeftPop("UserList");
        return true;
    }
}
