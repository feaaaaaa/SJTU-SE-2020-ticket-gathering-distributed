/**
 * @ClassName User
 * @Description User Dao Test
 * @Author ziliuziliu
 * @Date 2020/7/15
 */

package com.oligei.ticketgathering.dao;

import com.oligei.ticketgathering.entity.mongodb.UserMongoDB;
import com.oligei.ticketgathering.entity.mysql.Order;
import com.oligei.ticketgathering.entity.mysql.User;
import com.oligei.ticketgathering.entity.neo4j.UserNeo4j;
import com.oligei.ticketgathering.repository.UserMongoDBRepository;
import com.oligei.ticketgathering.repository.UserNeo4jRepository;
import com.oligei.ticketgathering.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
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
class UserDaoTest {

    @Autowired
    UserDao userDao;

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserMongoDBRepository userMongoDBRepository;

    @MockBean
    UserNeo4jRepository userNeo4jRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private String username1 = "ziliuziliu";
    private String username2 = "zlzl";
    private String password1 = "1234567";
    private String password2 = "123456";
    private Integer userId1 = 1;
    private Integer userId2 = 2;

//    @Test
//    @Rollback
//    void login() {
//        User tmp = new User(null,username1,null,null,null,encoder.encode(password1),
//                username1,null);
//
//        when(userRepository.checkUser(username1)).thenReturn(tmp);
//        when(userRepository.checkUser(username2)).thenReturn(null);
//
//        System.out.println("Correct username, correct password");
//        assertEquals(username1,userDao.login(username1,password1).getType());
//        System.out.println("Correct username, wrong password");
//        assertNull(userDao.login(username1,password2));
//        System.out.println("Wrong username, correct password");
//        assertNull(userDao.login(username2,password1));
//        System.out.println("Wrong username, wrong password");
//        assertNull(userDao.login(username2,password2));
//        System.out.println("Username null, password not null");
//        assertThrows(NullPointerException.class, ()-> userDao.login(null,password1),
//                "null username --UserDaoImpl login");
//        System.out.println("Username not null, password null");
//        assertThrows(NullPointerException.class, ()-> userDao.login(username1,null),
//                "null password --UserDaoImpl login");
//    }
//
//    @Test
//    @Rollback
//    void register() {
//        User tmp = new User(null,username1,"123","123","123",encoder.encode(password1),
//                "success","123");
//        User saved_user = new User(userId1,username1,"123","123","123",encoder.encode(password1),
//                "success","123");
//        UserMongoDB saved_user2 = new UserMongoDB(userId1,"123");
//        UserNeo4j saved_user3 = new UserNeo4j(String.valueOf(userId1),username1);
//
//        when(userRepository.save(argThat(Objects::nonNull))).thenReturn(saved_user);
//        when(userMongoDBRepository.save(argThat(Objects::nonNull))).thenReturn(saved_user2);
//        when(userNeo4jRepository.save(argThat(Objects::nonNull))).thenReturn(saved_user3);
//
//        System.out.println("User not null");
//        assertTrue(userDao.register(tmp));
//        verify(userRepository,times(1)).save(argThat(Objects::nonNull));
//        verify(userMongoDBRepository,times(1)).save(argThat(Objects::nonNull));
//        verify(userNeo4jRepository,times(1)).save(argThat(Objects::nonNull));
//        System.out.println("User null");
//        assertThrows(NullPointerException.class, ()-> userDao.register(null),
//                "null user --UserDaoImpl register");
//    }
//
//    @Test
//    @Rollback
//    void existsByUsername() {
//        User tmp = new User(null,username1,"123","123","123",encoder.encode(password1),
//                "success","123");
//
//        when(userRepository.findUserByUsername(argThat(username -> username.equals(username1)))).thenReturn(tmp);
//        when(userRepository.findUserByUsername(argThat(username -> !username.equals(username1)))).thenReturn(null);
//
//        System.out.println("Correct username");
//        assertTrue(userDao.existsByUsername(username1));
//        System.out.println("Wrong username");
//        assertFalse(userDao.existsByUsername(username2));
//        System.out.println("Null username");
//        assertThrows(NullPointerException.class, ()-> userDao.existsByUsername(null),
//                "null username --UserDaoImpl existsByUsername");
//    }

    @Test
    @Rollback
    void findUserByUserId() {
        User tmp = new User(userId1,"test","123","123","123",encoder.encode(password1),
                String.valueOf(userId1),null);
        UserMongoDB tmp2 = new UserMongoDB(userId1,"123");

        when(userRepository.findUserByUserId(argThat(new ArgumentMatcher<Integer>() {
            @Override
            public boolean matches(Integer userId) {
                return Objects.equals(userId, userId1);
            }
        }))).thenReturn(tmp);
        when(userRepository.findUserByUserId(argThat(new ArgumentMatcher<Integer>() {
            @Override
            public boolean matches(Integer userId) {
                return !(Objects.equals(userId, userId1));
            }
        }))).thenReturn(null);
        when(userMongoDBRepository.findByUserId(argThat(userId -> Objects.equals(userId, userId1)))).thenReturn(tmp2);
//        when(userMongoDBRepository.findByUserId(argThat(userId -> Objects.equals(userId, userId1)))).thenReturn(null);

        System.out.println("Correct userId");
        assertEquals("123",userDao.findUserByUserId(userId1).getPersonIcon());
        System.out.println("Wrong userId");
        assertNull(userDao.findUserByUserId(userId2));
        System.out.println("Null userId");
        assertThrows(NullPointerException.class, ()-> userDao.existsByUsername(null),
                "null userId --UserDaoImpl findUserByUserId");
    }
}

