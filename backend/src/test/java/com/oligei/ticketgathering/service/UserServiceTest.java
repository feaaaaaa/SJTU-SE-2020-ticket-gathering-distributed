/**
 * @ClassName User
 * @Description User Service Test
 * @Author ziliuziliu
 * @Date 2020/7/15
 */

package com.oligei.ticketgathering.service;

import com.oligei.ticketgathering.entity.mysql.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    private String username1 = "ziliuziliu";
    private String username2 = "ziliuziiu";
    private String password1 = "ziliuziliu0427!";
    private String password2 = "ziliuziliu0427";

    @Test
    @Rollback
    void login() {
        System.out.println("Correct username, Correct password");
        assertEquals(1,userService.login(username1,password1).getUserId());
        System.out.println("Wrong username, Correct password");
        assertNull(userService.login(username2, password1));
        System.out.println("Correct username, Wrong password");
        assertNull(userService.login(username1, password2));
    }

    @Test
    @Rollback
    void register() {
        User user = new User(null,"linda","Female","linda@qq.com","12345","linda","User",null);
        System.out.println("Inserting new user: linda");
        assertTrue(userService.register(user));
    }

    @Test
    @Rollback
    void existsByUsername() {
        System.out.println("Correct username");
        assertTrue(userService.existsByUsername(username1));
        System.out.println("Wrong username");
        assertFalse(userService.existsByUsername(username2));
    }
}
