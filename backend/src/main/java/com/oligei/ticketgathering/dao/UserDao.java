/**
 * @ClassName User
 * @Description User Dao
 * @Author ziliuziliu
 * @Date 2020/7/10
 */

package com.oligei.ticketgathering.dao;

import com.oligei.ticketgathering.entity.mysql.User;

public interface UserDao {

    /**
     * check username and password
     */
    User login(String username, String password);

    /**
     * decode password, save the user information in mongodb and mysql
     */
    boolean register(User user);

    /**
     * check whether the user is existed
     */
    boolean existsByUsername(String username);

    /**
     * get userInfo by userId
     */
    User findUserByUserId(Integer userId);

    /**
     * save a user to increment or decrement balance
     */
    User save(User user);
}
