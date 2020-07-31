/**
 * @ClassName UserNeo4jRelationship
 * @Description User Entity for Neo4j
 * @Author ziliuziliu
 * @Date 2020/7/16
 */

package com.oligei.ticket_gathering.repository;

import com.oligei.ticket_gathering.entity.neo4j.UserNeo4j;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserNeo4jRepository extends Neo4jRepository<UserNeo4j,Long> {

    @Query("MATCH (u:user) WHERE u.userId=$userId RETURN u")
    UserNeo4j findOneByUserId(String userId);

}
