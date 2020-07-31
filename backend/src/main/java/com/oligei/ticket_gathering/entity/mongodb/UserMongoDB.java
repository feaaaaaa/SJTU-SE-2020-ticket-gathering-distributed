/**
 * @ClassName User
 * @Description User Entity for MongoDB
 * @Author ziliuziliu
 * @Date 2020/7/9
 */

package com.oligei.ticket_gathering.entity.mongodb;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Document(collection = "tg_users")
public class UserMongoDB {
    @Id
    @Field("userid")
    private Integer userId;

    @Field("usericon")
    private String personIcon; /* base 64 */

    public UserMongoDB(Integer userId, String personIcon) {
        this.userId=userId;
        this.personIcon=personIcon;
    }

    public Integer getUserId() {return userId;}
    public void setUserId(Integer userId) {this.userId=userId;}

    public String getPersonIcon() {return personIcon;}
    public void setPersonIcon(String personIcon) {this.personIcon=personIcon;}
}
