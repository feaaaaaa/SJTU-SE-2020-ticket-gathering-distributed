package com.oligei.ticketgathering.daoimpl;

import com.oligei.ticketgathering.dao.UserDao;
import com.oligei.ticketgathering.entity.mongodb.UserMongoDB;
import com.oligei.ticketgathering.entity.mysql.User;
import com.oligei.ticketgathering.entity.neo4j.UserNeo4j;
import com.oligei.ticketgathering.repository.UserMongoDBRepository;
import com.oligei.ticketgathering.repository.UserNeo4jRepository;
import com.oligei.ticketgathering.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import java.util.Objects;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMongoDBRepository userMongoDBRepository;

    @Autowired
    private UserNeo4jRepository userNeo4jRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    /**
     *check username and password
     *@param username,password
     *@return the saved user or null
     *@author ziliuziliu,Yang Yicheng
     *@date 2020/8/18
     *@throws NullPointerException if username or password is null
     */
    public User login(String username, String password) {
        Objects.requireNonNull(username,"null username --UserDaoImpl login");
        Objects.requireNonNull(password,"null password --UserDaoImpl login");
        User user = userRepository.checkUser(username);
        if (user == null) return null;
        if (encoder.matches(password, user.getPassword())) return user;
        return null;
    }

    @Override
    /**
     *decode password, save the user information in mongodb&mysql
     *@param user
     *@return register success or fail
     *@author ziliuziliu,feaaaaaa
     *@date 2020/8/18
     *@throws NullPointerException if user is null or the personIcon or password or username of user is null
     *@throws InvalidDataAccessApiUsageException if invalid data(include null) used in repository
     */
    public boolean register(User user) {
        Objects.requireNonNull(user,"null user --UserDaoImpl register");
        String personIcon = user.getPersonIcon();
        user.setPersonIcon("");
        String rawPassword = user.getPassword();
        user.setPassword(encoder.encode(rawPassword));
        User saved_user = userRepository.save(user);
        Integer userId = saved_user.getUserId();
        String username = saved_user.getUsername();
        UserMongoDB userMongoDB = new UserMongoDB(userId, personIcon);
        userMongoDBRepository.save(userMongoDB);
        UserNeo4j userNeo4j = new UserNeo4j(String.valueOf(userId), username);
        userNeo4jRepository.save(userNeo4j);
        return true;
    }

    @Override
    /**
     *check whether the user is existed
     *@param username
     *@return true if existed, false if not
     *@author ziliuzilu,Yang Yicheng
     *@date 2020/8/18
     *@throws NullPointerException if username is null
     */
    public boolean existsByUsername(String username) {
        Objects.requireNonNull(username,"null username --UserDaoImpl existsByUsername");
        User user = userRepository.findUserByUsername(username);
        return user != null;
    }

    @Override
    /**
     *get userInfo by userId
     *@param userId
     *@return the user of certain userId, or null if not exists
     *@author ziliuziliu,Yang Yicheng
     *@date 2020/8/18
     *@throws NullPointerException if userId is null
     */
    public User findUserByUserId(Integer userId){
        Objects.requireNonNull(userId,"null userId --UserDaoImpl findUserByUserId");
        User user=userRepository.findUserByUserId(userId);
        UserMongoDB userIcon=userMongoDBRepository.findByUserId(userId);
        if (user == null || userIcon == null) return null;
        else {
            user.setPersonIcon(userIcon.getPersonIcon());
            return user;
        }
    }

    @Override
    /**
    *save a user to increment or decrement balance
    *@param: user
    *@return: com.oligei.ticketgathering.entity.mysql.User
    *@author: Cui Shaojie
    *@date: 2020/8/20
    */
    public User save(User user) {
        Objects.requireNonNull(user,"null user --UserDaoImpl save");
        return userRepository.save(user);
    }
}
