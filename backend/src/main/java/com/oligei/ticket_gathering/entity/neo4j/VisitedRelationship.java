/**
 * @ClassName VisitedRelationship
 * @Description User -> Activity
 * @Author ziliuziliu
 * @Date 2020/7/16
 */

package com.oligei.ticket_gathering.entity.neo4j;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "VISITED")
public class VisitedRelationship {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private UserNeo4j userNeo4j;

    @EndNode
    private ActivityNeo4j activityNeo4j;

    public VisitedRelationship() {}
    public VisitedRelationship(UserNeo4j userNeo4j, ActivityNeo4j activityNeo4j) {
        this.userNeo4j=userNeo4j;
        this.activityNeo4j=activityNeo4j;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id=id;}
    public UserNeo4j getUserNeo4j() {return userNeo4j;}
    public void setUserNeo4j(UserNeo4j userNeo4j) {this.userNeo4j=userNeo4j;}
    public ActivityNeo4j activityNeo4j() {return activityNeo4j;}
    public void setActivityNeo4j(ActivityNeo4j activityNeo4j) {this.activityNeo4j=activityNeo4j;}
}
