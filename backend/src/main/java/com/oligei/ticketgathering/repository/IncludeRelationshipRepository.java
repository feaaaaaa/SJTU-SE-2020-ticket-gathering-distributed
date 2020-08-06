/**
 * @ClassName IncludeRelationshipRepository
 * @Description IncludeRelationship Repository
 * @Author ziliuziliu
 * @Date 2020/7/16
 */

package com.oligei.ticketgathering.repository;

import com.oligei.ticketgathering.entity.neo4j.IncludeRelationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncludeRelationshipRepository extends Neo4jRepository<IncludeRelationship,Long> {
}
