/**
 * @ClassName SubcategoryNeo4jRepository
 * @Description SubcategoryNeo4j Repository
 * @Author ziliuziliu
 * @Date 2020/7/16
 */

package com.oligei.ticket_gathering.repository;

import com.oligei.ticket_gathering.entity.neo4j.SubcategoryNeo4j;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoryNeo4jRepository extends Neo4jRepository<SubcategoryNeo4j,Long> {
    SubcategoryNeo4j findByName(String subcategory);
}
