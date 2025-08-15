// CandidateRepository.java
package com.votingsystem.repository;

import com.votingsystem.entity.Candidate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends CrudRepository<Candidate, Long> {

    List<Candidate> findByElectionId(Long electionId);

    Optional<Candidate> findByCandidateName(String candidateName);

    List<Candidate> findByCandidateNameContaining(String candidateName);

    List<Candidate> findByParty(String party);

    List<Candidate> findByRegisteredById(Long adminId);

    @Query("SELECT c FROM Candidate c WHERE c.election.id = :electionId ORDER BY c.candidateName ASC")
    List<Candidate> findByElectionIdOrderByName(@Param("electionId") Long electionId);

    @Query("SELECT c FROM Candidate c JOIN c.election e WHERE e.status = 'ACTIVE'")
    List<Candidate> findCandidatesInActiveElections();

    @Query("SELECT COUNT(c) FROM Candidate c WHERE c.election.id = :electionId")
    long countByElectionId(@Param("electionId") Long electionId);
}