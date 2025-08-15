package com.votingsystem.service;

import com.votingsystem.dto.candidate.CandidateCreateRequest;
import com.votingsystem.dto.candidate.CandidateResponse;
import com.votingsystem.entity.Admin;
import com.votingsystem.entity.Candidate;
import com.votingsystem.entity.Election;
import com.votingsystem.exception.NotFoundException;
import com.votingsystem.repository.AdminRepository;
import com.votingsystem.repository.CandidateRepository;
import com.votingsystem.repository.ElectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CandidateService {

    private final CandidateRepository candidateRepo;
    private final ElectionRepository electionRepo;
    private final AdminRepository adminRepo;

    public CandidateService(CandidateRepository candidateRepo, ElectionRepository electionRepo, AdminRepository adminRepo) {
        this.candidateRepo = candidateRepo;
        this.electionRepo = electionRepo;
        this.adminRepo = adminRepo;
    }

    public CandidateResponse create(CandidateCreateRequest req) {
        Election election = electionRepo.findById(req.getElectionId())
                .orElseThrow(() -> new NotFoundException("Election not found"));
        Admin admin = adminRepo.findById(req.getAdminId())
                .orElseThrow(() -> new NotFoundException("Admin not found"));

        Candidate c = new Candidate(req.getCandidateName(), req.getParty(), req.getBiography(), election, admin);
        Candidate saved = candidateRepo.save(c);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<CandidateResponse> listByElection(Long electionId) {
        return candidateRepo.findByElectionId(electionId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void delete(Long candidateId) {
        Candidate c = candidateRepo.findById(candidateId)
                .orElseThrow(() -> new NotFoundException("Candidate not found"));
        candidateRepo.delete(c);
    }

    private CandidateResponse toResponse(Candidate c) {
        CandidateResponse dto = new CandidateResponse();
        dto.setId(c.getId());
        dto.setCandidateName(c.getCandidateName());
        dto.setParty(c.getParty());
        dto.setBiography(c.getBiography());
        dto.setElectionId(c.getElection().getId());
        dto.setRegisteredByAdminId(c.getRegisteredBy().getId());
        dto.setCreatedAt(c.getCreatedAt());
        return dto;
    }
}
