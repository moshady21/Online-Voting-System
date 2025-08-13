// ElectionRepository.java
package com.votingsystem.repository;

import com.votingsystem.entity.Election;
import com.votingsystem.entity.ElectionStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ElectionRepository extends CrudRepository<Election, Long> {

    List<Election> findByStatus(ElectionStatus status);

    List<Election> findByAdminId(Long adminId);

    @Query("SELECT e FROM Election e WHERE e.startTime <= :now AND e.endTime >= :now AND e.status = :status")
    List<Election> findActiveElections(@Param("now") LocalDateTime now, @Param("status") ElectionStatus status);

    @Query("SELECT e FROM Election e WHERE e.title LIKE %:title%")
    List<Election> findByTitleContaining(@Param("title") String title);

    @Query("SELECT e FROM Election e WHERE e.endTime < :now")
    List<Election> findCompletedElections(@Param("now") LocalDateTime now);

    @Query("SELECT e FROM Election e WHERE e.startTime > :now")
    List<Election> findUpcomingElections(@Param("now") LocalDateTime now);
}