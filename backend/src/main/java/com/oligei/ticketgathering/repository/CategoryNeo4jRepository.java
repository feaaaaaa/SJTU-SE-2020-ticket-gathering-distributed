/**
 * @ClassName CategoryNeo4jRepository
 * @Description CategoryNeo4j Repository
 * @Author ziliuziliu
 * @Date 2020/7/16
 */

package com.oligei.ticketgathering.repository;

import com.oligei.ticketgathering.entity.neo4j.CategoryNeo4j;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryNeo4jRepository extends Neo4jRepository<CategoryNeo4j,Long> {
}
