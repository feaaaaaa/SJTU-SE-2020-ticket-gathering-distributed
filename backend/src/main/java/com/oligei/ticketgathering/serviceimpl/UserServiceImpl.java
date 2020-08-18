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
     *@Description check username and password
     *@Param [username, password]
     *@return com.oligei.ticketgathering.entity.mysql.User
     *@Author Yang Yicheng
     *@date 2020/8/18
     */
    public User login(String username, String password) {
        return userDao.login(username, password);
    }

    @Override
    /**
     * @Description check if user is null & call userDao.register(user);
     * @param user  user to be registered
     * @return register success or fail
     * @Author
     * @date
     * @throws NullPointerException if user is null
     */
    public boolean register(User user) {
        //the first way to write
        Objects.requireNonNull(user,"null user --UserServiceImpl register");
        //the second way to write
//        if(user==null)
//            throw new NullPointerException("null user --UserServiceImpl register");
        return userDao.register(user);
    }

    @Override
    /**
     *@Description check whether the user is exsisted
     *@Param [username]
     *@return boolean
     *@Author Yang Yicheng
     *@date 2020/8/18
     */
    public boolean existsByUsername(String username) {
        return userDao.existsByUsername(username);
    }

    @Override
    /**
     *@Description get userInfo by userId
     *@Param [userId]
     *@return com.oligei.ticketgathering.entity.mysql.User
     *@Author Yang Yicheng
     *@date 2020/8/18
     *@Throws NullPointerException if userId is not exist
     */
    public User findUserByUserId(Integer userId){
        return userDao.findUserByUserId(userId);
    }
}
