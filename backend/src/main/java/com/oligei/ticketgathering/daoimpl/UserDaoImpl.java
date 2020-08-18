/**
 * @ClassName User
 * @Description User Dao Implementation
 * @Author ziliuziliu
 * @Date 2020/7/10
 */

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
    public User login(String username, String password) {
        /**
        *@Description check username and password
        *@Param [username, password]
        *@return com.oligei.ticketgathering.entity.mysql.User
        *@Author Yang Yicheng
        *@date 2020/8/18
        *@Throws NullPointerException user is null or the password is incorrrect
        */
//        User user=new User(1,"oligei","Male","123456","123456", "123456","123456","123456");
        User user = userRepository.checkUser(username);
        if (user != null && encoder.matches(password, user.getPassword())){
//            Integer userId = user.getUserId();
//            UserMongoDB user_mongodb = userMongoDBRepository.findByUserId(userId);
//            if (user_mongodb != null && user_mongodb.getPersonIcon() != null)
//                user.setPersonIcon(user_mongodb.getPersonIcon());
            return user;
        }
        else{
            throw new NullPointerException("user is null or the password is incorrrect");
        }
    }

    @Override
    /**
     *@description decode password, save the user information in mongodb&mysql
     *@param user  user to be registered
     *@return register success or fail
     *@author
     *@date
     *@throws NullPointerException if user is null or the personIcon or password or username of user is null
     *@throws InvalidDataAccessApiUsageException if invalid data(include null) used in repository
     */
    public boolean register(User user) {
        Objects.requireNonNull(user,"null user --UserDaoimpl register");
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
    public boolean existsByUsername(String username) {
        /**
        *@Description check whether the user is exsisted
        *@Param [username]
        *@return boolean
        *@Author Yang Yicheng
        *@date 2020/8/18
        */
        User user = userRepository.findUserByUsername(username);
        return user != null;
    }

    @Override
    public User findUserByUserId(Integer userId){
        /**
        *@Description get userInfo by userId
        *@Param [userId]
        *@return com.oligei.ticketgathering.entity.mysql.User
        *@Author Yang Yicheng
        *@date 2020/8/18
        *@Throws NullPointerException if userId is not exist
        */
        User user=userRepository.findUserByUserId(userId);
        UserMongoDB userIcon=userMongoDBRepository.findByUserId(userId);
        if(user==null ||userIcon==null){
            throw new NullPointerException("invalid userId");
        }
        else{
            user.setPersonIcon(userIcon.getPersonIcon());
            return user;
        }
    }
}
