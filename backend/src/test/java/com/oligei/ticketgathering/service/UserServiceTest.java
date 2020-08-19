/**
 * @ClassName User
 * @Description User Service Test
 * @Author ziliuziliu
 * @Date 2020/7/15
 */

package com.oligei.ticketgathering.service;

import com.oligei.ticketgathering.dao.UserDao;
import com.oligei.ticketgathering.entity.mongodb.UserMongoDB;
import com.oligei.ticketgathering.entity.mysql.User;
import com.oligei.ticketgathering.entity.neo4j.UserNeo4j;
import com.oligei.ticketgathering.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @MockBean
    private UserDao userDao;

    private String username1 = "ziliuziliu";
    private String username2 = "zlzl";
    private String password1 = "1234567";
    private String password2 = "123456";
    private Integer userId1 = 1;
    private Integer userId2 = 2;

    @Test
    @Rollback
    void login() {
        User tmp = new User(null,username1,null,null,null,encoder.encode(password1),
                username1,null);

        when(userDao.login(username1,password1)).thenReturn(tmp);
        when(userDao.login(anyString(),argThat(password -> Objects.equals(password,password2)))).thenReturn(null);
        when(userDao.login(argThat(username -> Objects.equals(username,username2)),anyString())).thenReturn(null);

        System.out.println("Correct username, correct password");
        assertEquals(username1,userService.login(username1,password1).getType());
        System.out.println("Correct username, wrong password");
        assertNull(userService.login(username1,password2));
        System.out.println("Wrong username, correct password");
        assertNull(userService.login(username2,password1));
        System.out.println("Wrong username, wrong password");
        assertNull(userService.login(username2,password2));
        System.out.println("Username null, password not null");
        assertThrows(NullPointerException.class, ()-> userService.login(null,password1),
                "null username --UserServiceImpl login");
        System.out.println("Username not null, password null");
        assertThrows(NullPointerException.class, ()-> userService.login(username1,null),
                "null password --UserServiceImpl login");
    }

    @Test
    @Rollback
    void register() {
        User tmp = new User(null,username1,"123","123","123",encoder.encode(password1),
                "success","123");

        when(userDao.register(tmp)).thenReturn(true);
        when(userDao.register(argThat(user -> !Objects.equals(user,tmp)))).thenReturn(false);

        System.out.println("User not null");
        assertTrue(userService.register(tmp));
        System.out.println("User null");
        assertThrows(NullPointerException.class, ()-> userService.register(null),
                "null user --UserServiceImpl register");
    }

    @Test
    @Rollback
    void existsByUsername() {
        when(userDao.existsByUsername(username1)).thenReturn(true);
        when(userDao.existsByUsername(argThat(username -> !Objects.equals(username,username1)))).thenReturn(false);

        System.out.println("Correct username");
        assertTrue(userService.existsByUsername(username1));
        System.out.println("Wrong username");
        assertFalse(userService.existsByUsername(username2));
        System.out.println("Null username");
        assertThrows(NullPointerException.class, ()-> userService.existsByUsername(null),
                "null username --UserServiceImpl existsByUsername");
    }

    @Test
    @Rollback
    void findUserByUserId() {
        User tmp = new User(userId1,"test","123","123","123",encoder.encode(password1),
                String.valueOf(userId1),"123");

        when(userDao.findUserByUserId(userId1)).thenReturn(tmp);
        when(userDao.findUserByUserId(argThat(userId -> !Objects.equals(userId,userId1)))).thenReturn(null);

        System.out.println("Correct userId");
        assertEquals("123",userService.findUserByUserId(userId1).getPersonIcon());
        System.out.println("Wrong userId");
        assertNull(userService.findUserByUserId(userId2));
        System.out.println("Null userId");
        assertThrows(NullPointerException.class, ()-> userService.existsByUsername(null),
                "null userId --UserServiceImpl findUserByUserId");
    }
}
