/**
 * @ClassName SubcategoryNeo4j
 * @Description Subcategory Entity for Neo4j
 * @Author ziliuziliu
 * @Date 2020/7/16
 */

package com.oligei.ticketgathering.entity.neo4j;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "subcategory")
public class SubcategoryNeo4j {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    public SubcategoryNeo4j() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id=id;}
    public String getName() {return name;}
    public void setName(String name) {this.name=name;}
}
