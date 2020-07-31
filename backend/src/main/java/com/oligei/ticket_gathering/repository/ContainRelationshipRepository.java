/**
 * @ClassName ContainRelationshipRepository
 * @Description ContainRelationship Repository
 * @Author ziliuziliu
 * @Date 2020/7/16
 */

package com.oligei.ticket_gathering.repository;

import com.oligei.ticket_gathering.entity.neo4j.ContainRelationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainRelationshipRepository extends Neo4jRepository<ContainRelationship,Long> {
}
