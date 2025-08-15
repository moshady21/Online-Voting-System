package com.votingsystem.dto.results;

public class CandidateResultResponse {
    private Long candidateId;
    private String candidateName;
    private String party;
    private long votes;

    public CandidateResultResponse() {}
    public CandidateResultResponse(Long candidateId, String candidateName, String party, long votes) {
        this.candidateId = candidateId;
        this.candidateName = candidateName;
        this.party = party;
        this.votes = votes;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public long getVotes() {
        return votes;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }
}
