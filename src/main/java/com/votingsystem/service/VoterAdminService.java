package com.votingsystem.service;

import com.votingsystem.dto.voter.AssignVotersRequest;
import com.votingsystem.entity.Election;
import com.votingsystem.entity.Voter;
import com.votingsystem.exception.NotFoundException;
import com.votingsystem.repository.ElectionRepository;
import com.votingsystem.repository.VoterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class VoterAdminService {

    private final VoterRepository voterRepo;
    private final ElectionRepository electionRepo;

    public VoterAdminService(VoterRepository voterRepo, ElectionRepository electionRepo) {
        this.voterRepo = voterRepo;
        this.electionRepo = electionRepo;
    }

    /** Assign a list of voters to an election (Many-to-Many) */
    public Map<String, Object> assignVotersToElection(AssignVotersRequest req) {
        Election election = electionRepo.findById(req.getElectionId())
                .orElseThrow(() -> new NotFoundException("Election not found"));

        List<Voter> voters = new ArrayList<>();
        for (Long id : req.getVoterIds()) {
            Voter v = voterRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Voter not found: " + id));
            if (!election.getEligibleVoters().contains(v)) {
                election.getEligibleVoters().add(v);
            }
            if (!v.getEligibleElections().contains(election)) {
                v.getEligibleElections().add(election);
            }
            v.setAssigned(true);
            voters.add(v);
        }
        // JPA will flush via transaction
        Map<String, Object> result = new HashMap<>();
        result.put("electionId", election.getId());
        result.put("assignedVoterIds", voters.stream().map(Voter::getId).collect(Collectors.toList()));
        return result;
    }

    @Transactional(readOnly = true)
    public List<Voter> listEligibleVotersForElection(Long electionId) {
        Election election = electionRepo.findById(electionId)
                .orElseThrow(() -> new NotFoundException("Election not found"));
        return election.getEligibleVoters();
    }

    @Transactional(readOnly = true)
    public List<Voter> listUnassignedByCity(String city) {
        return voterRepo.findUnassignedVotersByCity(city);
    }
}
