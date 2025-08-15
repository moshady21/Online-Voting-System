package com.votingsystem.service;

import com.votingsystem.dto.vote.VoteRequestDTO;
import com.votingsystem.dto.vote.VoteResponseDTO;
import com.votingsystem.entity.*;
import com.votingsystem.exception.VotingClosedException;
import com.votingsystem.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VotingService {

    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;
    private final ElectionRepository electionRepository;
    private final VoteRepository voteRepository;

    public VotingService(VoterRepository voterRepository,
                         CandidateRepository candidateRepository,
                         ElectionRepository electionRepository,
                         VoteRepository voteRepository) {
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
        this.electionRepository = electionRepository;
        this.voteRepository = voteRepository;
    }

    @Transactional
    public VoteResponseDTO castVote(VoteRequestDTO request) {

        // 1️⃣ Get Voter
        Voter voter = voterRepository.findById(request.getVoterId())
                .orElseThrow(() -> new RuntimeException("Voter not found"));

        // Prevent multiple votes in same election
        boolean alreadyVoted = voteRepository.existsByVoterIdAndElectionId(voter.getId(), request.getElectionId());
        if (alreadyVoted) {
            throw new RuntimeException("Voter has already voted in this election");
        }

        // 2️⃣ Get Candidate
        Candidate candidate = candidateRepository.findByCandidateName(request.getCandidateName())
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        // 3️⃣ Get Election
        Election election = electionRepository.findById(request.getElectionId())
                .orElseThrow(() -> new RuntimeException("Election not found"));

        // Ensure election is active
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(election.getStartTime()) || now.isAfter(election.getEndTime())) {
            throw new VotingClosedException("Voting is not allowed outside the election time window");
        }

        // Ensure election is active
        if (!election.isVotingActive()) {
            throw new RuntimeException("Voting is not active for this election");
        }

        // 4️⃣ Create Vote
        Vote vote = new Vote();
        vote.setVoter(voter);
        vote.setCandidate(candidate);
        vote.setElection(election);

        voteRepository.save(vote);

        // 5️⃣ Build Response
        VoteResponseDTO response = new VoteResponseDTO();
        response.setVoteId(vote.getId());
        response.setVoterName(voter.getName());
        response.setCandidateName(candidate.getCandidateName());
        response.setElectionName(election.getTitle());
        response.setTimestamp(LocalDateTime.now());
        response.setMessage("Successful");

        return response;
    }
}
