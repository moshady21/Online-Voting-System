package com.votingsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "candidates")
@Data
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Candidate name is required")
    @Column(nullable = false)
    private String candidateName;

    @NotBlank(message = "Party is required")
    @Column(nullable = false)
    private String party;

    @Column(columnDefinition = "TEXT")
    private String biography;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_by", nullable = false)
    private Admin registeredBy;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vote> votes = new ArrayList<>();

    // Constructors
    public Candidate() {
    }

    public Candidate(String candidateName, String party, String biography, Election election, Admin registeredBy) {
        this.candidateName = candidateName;
        this.party = party;
        this.biography = biography;
        this.election = election;
        this.registeredBy = registeredBy;
    }
}