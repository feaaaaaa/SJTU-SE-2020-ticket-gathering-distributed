/**
 * @ClassName ContainRelationship
 * @Description Category -> Sucategory
 * @Author ziliuziliu
 * @Date 2020/7/16
 */

package com.oligei.ticketgathering.entity.neo4j;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "INCLUDES")
public class ContainRelationship {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private CategoryNeo4j categoryNeo4j;

    @EndNode
    private SubcategoryNeo4j subcategoryNeo4j;

    public ContainRelationship() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id=id;}
    public CategoryNeo4j getCategoryNeo4j() {return categoryNeo4j;}
    public void setCategoryNeo4j(CategoryNeo4j categoryNeo4j) {
        this.categoryNeo4j=categoryNeo4j;
    }
    public SubcategoryNeo4j getSubcategoryNeo4j() {return subcategoryNeo4j;}
    public void setSubcategoryNeo4j(SubcategoryNeo4j subcategoryNeo4j) {
        this.subcategoryNeo4j=subcategoryNeo4j;
    }
}
