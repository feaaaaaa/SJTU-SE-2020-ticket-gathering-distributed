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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    /**
     *check username and password
     *@param username,password
     *@return the saved user or null
     *@author ziliuziliu,Yang Yicheng
     *@date 2020/8/18
     *@throws NullPointerException username or password is null
     */
    public User login(String username, String password) {
        Objects.requireNonNull(username,"null username --UserServiceImpl login");
        Objects.requireNonNull(password,"null password --UserServiceImpl login");
        return userDao.login(username, password);
    }

    @Override
    /**
     * check if user is null & call userDao.register(user);
     * @param user  user to be registered
     * @return register success or fail
     * @author ziliuziliu,feaaaaaa
     * @date 2020/8/18
     * @throws NullPointerException if user is null
     */
    public boolean register(User user) {
        Objects.requireNonNull(user,"null user --UserServiceImpl register");
        user.setBalance(500);
        return userDao.register(user);
    }

    @Override
    /**
     *check whether the user is exsisted
     *@param username
     *@return true if exists, false if not
     *@Author ziliuziliu,Yang Yicheng
     *@date 2020/8/18
     *@throws NullPointerException username null
     */
    public boolean existsByUsername(String username) {
        Objects.requireNonNull(username,"null username --UserServiceImpl existsByUsername");
        return userDao.existsByUsername(username);
    }

    @Override
    /**
     *get userInfo by userId
     *@param userId
     *@return User or null
     *@author ziliuziliu,Yang Yicheng
     *@date 2020/8/18
     *@throws NullPointerException userId null
     */
    public User findUserByUserId(Integer userId){
        Objects.requireNonNull(userId,"null userId --UserServiceImpl findUserByUserId");
        return userDao.findUserByUserId(userId);
    }

    @Override
    /**
    *recharge one's balance
    *@param: userid,increment
    *@return: java.lang.Integer
    *@author: Cui Shaojie
    *@date: 2020/8/20
    *@throws NullPointerException userid null
    */
    public Integer rechargeOrDeduct(Integer userid, Integer increment) {
        Objects.requireNonNull(userid,"null userid --UserServiceImpl rechargeOrDeduct");
        Objects.requireNonNull(increment,"null increment --UserServiceImpl rechargeOrDeduct");
        User user = userDao.findUserByUserId(userid);
        Objects.requireNonNull(user,"null user --UserServiceImpl rechargeOrDeduct");
        int result = user.getBalance()+increment;
        if(result < 0)
            return -1;

        user.setBalance(result);

        userDao.save(user);

        return result;
    }
}
