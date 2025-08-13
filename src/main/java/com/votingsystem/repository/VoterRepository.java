package com.votingsystem.repository;

import com.votingsystem.entity.Voter;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoterRepository extends CrudRepository<Voter, Long> {

    Optional<Voter> findByEmail(String email);

    List<Voter> findByCity(String city);

    List<Voter> findByCityIgnoreCase(String city);

    @Query("SELECT v FROM Voter v WHERE v.city = :city AND v.isAssigned = false")
    List<Voter> findUnassignedVotersByCity(@Param("city") String city);

    @Query("SELECT v FROM Voter v WHERE v.isAssigned = true")
    List<Voter> findAssignedVoters();

    @Modifying
    @Query("UPDATE Voter v SET v.isAssigned = true WHERE v.id IN :voterIds")
    void assignVoters(@Param("voterIds") List<Long> voterIds);

    @Modifying
    @Query("UPDATE Voter v SET v.isAssigned = false WHERE v.id = :voterId")
    void unassignVoter(@Param("voterId") Long voterId);
}