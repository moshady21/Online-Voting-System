package com.votingsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("VOTER")
public class Voter extends User {

    @NotBlank(message = "City is required")
    @Column(nullable = false)
    private String city;

    @Column(name = "is_assigned")
    private boolean isAssigned = false;

    @OneToMany(mappedBy = "voter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vote> votes = new ArrayList<>();

    @ManyToMany(mappedBy = "eligibleVoters")
    private List<Election> eligibleElections = new ArrayList<>();

    public Voter() {
        super();
    }

    public Voter(String email, String password, String name, String city) {
        super(email, password, name, Role.VOTER);
        this.city = city;
    }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public boolean isAssigned() { return isAssigned; }
    public void setAssigned(boolean assigned) { isAssigned = assigned; }

    public List<Vote> getVotes() { return votes; }
    public void setVotes(List<Vote> votes) { this.votes = votes; }

    public List<Election> getEligibleElections() { return eligibleElections; }
    public void setEligibleElections(List<Election> eligibleElections) { this.eligibleElections = eligibleElections; }
}