package com.votingsystem.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Election> electionsManaged = new ArrayList<>();

    @OneToMany(mappedBy = "registeredBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Candidate> candidatesRegistered = new ArrayList<>();

    public Admin() {
        super();
    }

    public Admin(String email, String password, String name) {
        super(email, password, name, Role.ADMIN);
    }

    public List<Election> getElectionsManaged() { return electionsManaged; }
    public void setElectionsManaged(List<Election> electionsManaged) { this.electionsManaged = electionsManaged; }

    public List<Candidate> getCandidatesRegistered() { return candidatesRegistered; }
    public void setCandidatesRegistered(List<Candidate> candidatesRegistered) { this.candidatesRegistered = candidatesRegistered; }
}