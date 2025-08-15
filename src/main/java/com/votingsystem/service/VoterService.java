package com.votingsystem.service;

import com.votingsystem.entity.Voter;
import com.votingsystem.entity.Vote;
import com.votingsystem.entity.Election;
import com.votingsystem.repository.VoterRepository;
import com.votingsystem.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoterService {

    private final VoterRepository voterRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public VoterService(VoterRepository voterRepository, VoteRepository voteRepository) {
        this.voterRepository = voterRepository;
        this.voteRepository = voteRepository;
    }

    /** Register a new voter */
    public Voter registerVoter(Voter voter) {
        return voterRepository.save(voter);
    }

    /** Get all voters */
    public List<Voter> getAllVoters() {
        return (List<Voter>) voterRepository.findAll();
    }

    /** Get voter by ID */
    public Optional<Voter> getVoterById(Long id) {
        return voterRepository.findById(id);
    }

    /** Update voter details */
    public Optional<Voter> updateVoter(Long id, Voter updatedVoter) {
        return voterRepository.findById(id).map(existingVoter -> {
            existingVoter.setName(updatedVoter.getName());
            existingVoter.setEmail(updatedVoter.getEmail());
            existingVoter.setCity(updatedVoter.getCity());
            existingVoter.setPassword(updatedVoter.getPassword());
            existingVoter.setAssigned(updatedVoter.isAssigned());
            return voterRepository.save(existingVoter);
        });
    }

    /** Delete voter by ID */
    public boolean deleteVoter(Long id) {
        if (voterRepository.existsById(id)) {
            voterRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /** Check if voter has already voted in a given election */
    public boolean hasVoted(Long voterId, Long electionId) {
        return voteRepository.existsByVoterIdAndElectionId(voterId, electionId);
    }

    /** Get all votes for a voter */
    public List<Vote> getVotesByVoter(Long voterId) {
        return voteRepository.findByVoterId(voterId);
    }

    /** Get elections a voter is eligible for */
    public List<Election> getEligibleElections(Long voterId) {
        return voterRepository.findById(voterId)
                .map(Voter::getEligibleElections)
                .orElse(List.of());
    }

    /** Assign a voter to an election */
    public Optional<Voter> assignToElection(Long voterId, Election election) {
        return voterRepository.findById(voterId).map(voter -> {
            if (!voter.getEligibleElections().contains(election)) {
                voter.getEligibleElections().add(election);
            }
            voter.setAssigned(true);
            return voterRepository.save(voter);
        });
    }
}
