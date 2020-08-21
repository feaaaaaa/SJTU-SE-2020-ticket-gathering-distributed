/**
 * @ClassName User
 * @Description User Dao
 * @Author ziliuziliu
 * @Date 2020/7/10
 */

package com.oligei.ticketgathering.dao;

import com.oligei.ticketgathering.entity.mysql.User;

public interface UserDao {
    User login(String username, String password);
    boolean register(User user);
    boolean existsByUsername(String username);
    User findUserByUserId(Integer userId);
    User save(User user);
}
