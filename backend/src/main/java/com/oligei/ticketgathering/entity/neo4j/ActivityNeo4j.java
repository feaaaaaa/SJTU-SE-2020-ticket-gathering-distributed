/**
 * @ClassName ActivityNeo4j
 * @Description Activity Entity for Neo4j
 * @Author ziliuziliu
 * @Date 2020/7/16
 */

package com.oligei.ticketgathering.entity.neo4j;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "activity")
public class ActivityNeo4j {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "activityId")
    private String activityId;

    @Property(name = "category")
    private String category;

    @Property(name = "subcategory")
    private String subcategory;

    @Property(name = "city")
    private String city;

    public ActivityNeo4j() {}
    public ActivityNeo4j(String activityId, String category, String subcategory, String city) {
        this.activityId=activityId;
        this.category=category;
        this.subcategory=subcategory;
        this.city=city;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id=id;}
    public String getActivityId() {return activityId;}
    public void setActivityId(String activityId) {this.activityId=activityId;}
    public String getCategory() {return category;}
    public void setCategory(String category) {this.category=category;}
    public String getSubcategory() {return subcategory;}
    public void setSubcategory(String subcategory) {this.subcategory=subcategory;}
    public String getCity() {return city;}
    public void setCity(String city) {this.city=city;}
}
