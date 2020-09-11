/**
 * @ClassName User
 * @Description User Service
 * @Author ziliuziliu
 * @Date 2020/7/10
 */

package com.oligei.ticketgathering.service;

import com.oligei.ticketgathering.entity.mysql.User;

public interface UserService {

    /**
     * check username and password
     */
    User login(String username, String password);

    /**
     * check if user is null and call userDao.register(user);
     */
    boolean register(User user);

    /**
     * check whether the user is exsisted
     */
    boolean existsByUsername(String username);

    /**
     * get userInfo by userId
     */
    User findUserByUserId(Integer userId);

    /**
     * recharge one's balance
     */
    Integer rechargeOrDeduct(Integer userid, Integer increment);

    /**
     * flush user info back to database
     */
    Boolean flushUser();
}
