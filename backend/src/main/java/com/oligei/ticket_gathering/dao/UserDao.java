/**
 * @ClassName User
 * @Description User Dao
 * @Author ziliuziliu
 * @Date 2020/7/10
 */

package com.oligei.ticket_gathering.dao;

import com.oligei.ticket_gathering.entity.mysql.User;
import org.springframework.data.repository.query.Param;

public interface UserDao {
    User login(String username, String password);
    boolean register(User user);
    boolean existsByUsername(String username);
    User findUserByUserId(Integer userId);
}
