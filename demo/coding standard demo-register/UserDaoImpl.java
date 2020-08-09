/**
 * @ClassName User
 * @Description User Dao Implementation
 * @Author ziliuziliu
 * @Date 2020/7/10
 */

package com.oligei.ticket_gathering.daoimpl;

import com.oligei.ticket_gathering.dao.UserDao;
import com.oligei.ticket_gathering.entity.mongodb.UserMongoDB;
import com.oligei.ticket_gathering.entity.mysql.User;
import com.oligei.ticket_gathering.entity.neo4j.UserNeo4j;
import com.oligei.ticket_gathering.repository.UserMongoDBRepository;
import com.oligei.ticket_gathering.repository.UserNeo4jRepository;
import com.oligei.ticket_gathering.repository.UserRepository;
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
//        User user=new User(1,"oligei","Male","123456","123456", "123456","123456","123456");
        User user = userRepository.checkUser(username);
        if (user != null && encoder.matches(password, user.getPassword())){
//            Integer userId = user.getUserId();
//            UserMongoDB user_mongodb = userMongoDBRepository.findByUserId(userId);
//            if (user_mongodb != null && user_mongodb.getPersonIcon() != null)
//                user.setPersonIcon(user_mongodb.getPersonIcon());
            return user;
        }
        return null;
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
        User user = userRepository.findUserByUsername(username);
        return user != null;
    }

    @Override
    public User findUserByUserId(Integer userId){
        User user=userRepository.findUserByUserId(userId);
        UserMongoDB userIcon=userMongoDBRepository.findByUserId(userId);
        user.setPersonIcon(userIcon.getPersonIcon());
        return user;
    }
}
