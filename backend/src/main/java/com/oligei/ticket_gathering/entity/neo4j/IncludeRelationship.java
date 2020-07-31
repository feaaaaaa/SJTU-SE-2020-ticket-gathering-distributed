/**
 * @ClassName IncludeRelationship
 * @Description Subcategory -> Activity
 * @Author ziliuziliu
 * @Date 2020/7/16
 */

package com.oligei.ticket_gathering.entity.neo4j;

import com.oligei.ticket_gathering.entity.mysql.Activity;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "INCLUDES")
public class IncludeRelationship {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private SubcategoryNeo4j subcategoryNeo4j;

    @EndNode
    private ActivityNeo4j activityNeo4j;

    public IncludeRelationship() {}
    public IncludeRelationship(SubcategoryNeo4j subcategoryNeo4j, ActivityNeo4j activityNeo4j) {
        this.subcategoryNeo4j=subcategoryNeo4j;
        this.activityNeo4j=activityNeo4j;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id=id;}
    public SubcategoryNeo4j getSubcategoryNeo4j() {return subcategoryNeo4j;}
    public void setSubcategoryNeo4j(SubcategoryNeo4j subcategoryNeo4j) {
        this.subcategoryNeo4j=subcategoryNeo4j;
    }
    public ActivityNeo4j getActivityNeo4j() {return activityNeo4j;}
    public void setActivityNeo4j(ActivityNeo4j activityNeo4j) {
        this.activityNeo4j=activityNeo4j;
    }
}
