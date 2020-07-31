/**
 * @ClassName UserNeo4j
 * @Description User Entity for Neo4j
 * @Author ziliuziliu
 * @Date 2020/7/9
 */

package com.oligei.ticket_gathering.entity.neo4j;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "user")
public class UserNeo4j {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "userId")
    private String userId;

    @Property(name = "username")
    private String username;

    public UserNeo4j() {}
    public UserNeo4j(String userId, String username) {
        this.userId=userId;
        this.username=username;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id=id;}
    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId=userId;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username=username;}
}
