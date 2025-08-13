
// VoteRepository.java
package com.votingsystem.repository;

import com.votingsystem.entity.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {

    Optional<Vote> findByVoterIdAndElectionId(Long voterId, Long electionId);

    List<Vote> findByElectionId(Long electionId);

    List<Vote> findByCandidateId(Long candidateId);

    List<Vote> findByVoterId(Long voterId);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.candidate.id = :candidateId")
    long countByCandidateId(@Param("candidateId") Long candidateId);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.election.id = :electionId")
    long countByElectionId(@Param("electionId") Long electionId);

    @Query("SELECT v.candidate.id, COUNT(v) FROM Vote v WHERE v.election.id = :electionId GROUP BY v.candidate.id ORDER BY COUNT(v) DESC")
    List<Object[]> countVotesByCandidateInElection(@Param("electionId") Long electionId);

    boolean existsByVoterIdAndElectionId(Long voterId, Long electionId);
}