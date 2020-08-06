package com.oligei.ticketgathering.entity.neo4j;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "LOCATED")
public class LocatedRelationship {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private CityNeo4j cityNeo4j;

    @EndNode
    private ActivityNeo4j activityNeo4j;

    public LocatedRelationship() {}
    public LocatedRelationship(CityNeo4j cityNeo4j, ActivityNeo4j activityNeo4j) {
        this.cityNeo4j=cityNeo4j;
        this.activityNeo4j=activityNeo4j;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id=id;}
    public CityNeo4j getCityNeo4j() {return cityNeo4j;}
    public void setCityNeo4j(CityNeo4j cityNeo4j) {this.cityNeo4j=cityNeo4j;}
    public ActivityNeo4j getActivityNeo4j() {return activityNeo4j;}
    public void setActivityNeo4j(ActivityNeo4j activityNeo4j) {this.activityNeo4j=activityNeo4j;}

}
