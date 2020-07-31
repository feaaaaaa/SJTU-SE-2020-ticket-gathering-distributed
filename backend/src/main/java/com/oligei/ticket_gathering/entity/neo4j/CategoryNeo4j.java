/**
 * @ClassName CategoryNeo4j
 * @Description Category Entity for Neo4j
 * @Author ziliuziliu
 * @Date 2020/7/16
 */

package com.oligei.ticket_gathering.entity.neo4j;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "category")
public class CategoryNeo4j {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    public CategoryNeo4j() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id=id;}
    public String getName() {return name;}
    public void setName(String name) {this.name=name;}
}
