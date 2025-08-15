package com.votingsystem.dto.candidate;

import java.time.LocalDateTime;

public class CandidateResponse {
    private Long id;
    private String candidateName;
    private String party;
    private String biography;
    private Long electionId;
    private Long registeredByAdminId;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Long getElectionId() {
        return electionId;
    }

    public void setElectionId(Long electionId) {
        this.electionId = electionId;
    }

    public Long getRegisteredByAdminId() {
        return registeredByAdminId;
    }

    public void setRegisteredByAdminId(Long registeredByAdminId) {
        this.registeredByAdminId = registeredByAdminId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
