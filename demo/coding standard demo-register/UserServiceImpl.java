/**
 * @ClassName User
 * @Description User Service Implementation
 * @Author ziliuziliu
 * @Date 2020/7/9
 */

package com.oligei.ticket_gathering.serviceimpl;

import com.oligei.ticket_gathering.dao.UserDao;
import com.oligei.ticket_gathering.entity.mysql.User;
import com.oligei.ticket_gathering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
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
    public boolean existsByUsername(String username) {
        return userDao.existsByUsername(username);
    }

    @Override
    public User findUserByUserId(Integer userId){
        return userDao.findUserByUserId(userId);
    }
}
