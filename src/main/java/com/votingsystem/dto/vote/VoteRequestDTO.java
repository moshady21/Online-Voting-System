package com.votingsystem.dto.vote;

public class VoteRequestDTO {
    private Long voterId;
    private Long electionId;
    private String candidateName;

    public VoteRequestDTO() {}

    public VoteRequestDTO(Long voterId, Long electionId, String candidateName) {
        this.voterId = voterId;
        this.electionId = electionId;
        this.candidateName = candidateName;
    }

    public Long getVoterId() { return voterId; }
    public void setVoterId(Long voterId) { this.voterId = voterId; }

    public Long getElectionId() { return electionId; }
    public void setElectionId(Long electionId) { this.electionId = electionId; }

    public String getCandidateName() { return candidateName; }
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }
}

