/**
 * @ClassName User
 * @Description User Service
 * @Author ziliuziliu
 * @Date 2020/7/10
 */

package com.oligei.ticketgathering.service;

import com.oligei.ticketgathering.entity.mysql.User;

public interface UserService {
    User login(String username, String password);
    boolean register(User user);
    boolean existsByUsername(String username);
    User findUserByUserId(Integer userId);
}
