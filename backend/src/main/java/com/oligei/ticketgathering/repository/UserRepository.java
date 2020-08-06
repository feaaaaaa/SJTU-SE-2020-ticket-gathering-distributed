/**
 * @ClassName User
 * @Description User Repository
 * @Author ziliuziliu
 * @Date 2020/7/9
 */

package com.oligei.ticketgathering.repository;

import com.oligei.ticketgathering.entity.mysql.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("select u from User u where u.username = :username")
    User checkUser(@Param("username") String username);

    @Query("select u from User u where u.username = :username")
    User findUserByUsername(@Param("username") String username);

    @Query("select u from User u where u.userId = :userId")
    User findUserByUserId(@Param("userId") Integer userId);
}
