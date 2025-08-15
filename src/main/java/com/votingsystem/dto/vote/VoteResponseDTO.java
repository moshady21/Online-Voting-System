package com.votingsystem.dto.vote;

import java.time.LocalDateTime;

public class VoteResponseDTO {
    private Long voteId;
    private String voterName;
    private String electionName;
    private String candidateName;
    private LocalDateTime timestamp;
    private String message;

    public VoteResponseDTO() {}

    public VoteResponseDTO(String errorMessage){
        message = errorMessage;
    }

    public VoteResponseDTO(Long voteId, String voterName, String electionName, String candidateName, LocalDateTime timestamp) {
        this.voteId = voteId;
        this.voterName = voterName;
        this.electionName = electionName;
        this.candidateName = candidateName;
        this.timestamp = timestamp;
        this.message = "Successful";
    }

    public Long getVoteId() { return voteId; }
    public void setVoteId(Long voteId) { this.voteId = voteId; }

    public String getVoterName() { return voterName; }
    public void setVoterName(String voterName) { this.voterName = voterName; }

    public String getElectionName() { return electionName; }
    public void setElectionName(String electionName) { this.electionName = electionName; }

    public String getCandidateName() { return candidateName; }
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
