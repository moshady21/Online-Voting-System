package com.votingsystem.service;

import com.votingsystem.dto.election.*;
import com.votingsystem.dto.results.CandidateResultResponse;
import com.votingsystem.entity.*;
import com.votingsystem.exception.BadRequestException;
import com.votingsystem.exception.NotFoundException;
import com.votingsystem.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ElectionService {

    private final ElectionRepository electionRepo;
    private final AdminRepository adminRepo;
    private final CandidateRepository candidateRepo;
    private final VoteRepository voteRepo;

    public ElectionService(ElectionRepository electionRepo, AdminRepository adminRepo,
                           CandidateRepository candidateRepo, VoteRepository voteRepo) {
        this.electionRepo = electionRepo;
        this.adminRepo = adminRepo;
        this.candidateRepo = candidateRepo;
        this.voteRepo = voteRepo;
    }

    public ElectionResponse create(ElectionCreateRequest req) {
        if (req.getEndTime().isBefore(req.getStartTime())) {
            throw new BadRequestException("endTime must be after startTime");
        }
        Admin admin = adminRepo.findById(req.getAdminId())
                .orElseThrow(() -> new NotFoundException("Admin not found"));

        Election e = new Election(req.getTitle(), req.getDescription(), req.getStartTime(), req.getEndTime(), admin);
        e.setStatus(ElectionStatus.PENDING);
        Election saved = electionRepo.save(e);
        return toResponse(saved);
    }

    public ElectionResponse update(Long id, ElectionUpdateRequest req) {
        if (req.getEndTime().isBefore(req.getStartTime())) {
            throw new BadRequestException("endTime must be after startTime");
        }
        Election e = electionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Election not found"));
        e.setTitle(req.getTitle());
        e.setDescription(req.getDescription());
        e.setStartTime(req.getStartTime());
        e.setEndTime(req.getEndTime());
        return toResponse(e);
    }

    public void delete(Long id) {
        Election e = electionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Election not found"));
        electionRepo.delete(e);
    }

    @Transactional(readOnly = true)
    public ElectionResponse getById(Long id) {
        Election e = electionRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Election not found"));
        return toResponse(e);
    }

    @Transactional(readOnly = true)
    public List<ElectionResponse> listAll() {
        return ((List<Election>) electionRepo.findAll()).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CandidateResultResponse> getResults(Long electionId) {
        Election e = electionRepo.findById(electionId)
                .orElseThrow(() -> new NotFoundException("Election not found"));

        // use the custom query (already sorted DESC)
        List<Object[]> rows = voteRepo.countVotesByCandidateInElection(electionId);

        // If no votes yet, still show candidates with 0
        if (rows.isEmpty()) {
            return candidateRepo.findByElectionId(electionId).stream()
                    .map(c -> new CandidateResultResponse(c.getId(), c.getCandidateName(), c.getParty(), 0))
                    .collect(Collectors.toList());
        }

        // map candidateId -> count
        return rows.stream().map(r -> {
            Long candId = (Long) r[0];
            long count = (Long) r[1];
            Candidate c = candidateRepo.findById(candId)
                    .orElseThrow(() -> new NotFoundException("Candidate not found in results"));
            return new CandidateResultResponse(c.getId(), c.getCandidateName(), c.getParty(), count);
        }).collect(Collectors.toList());
    }

    private ElectionResponse toResponse(Election e) {
        ElectionResponse dto = new ElectionResponse();
        dto.setId(e.getId());
        dto.setTitle(e.getTitle());
        dto.setDescription(e.getDescription());
        dto.setStartTime(e.getStartTime());
        dto.setEndTime(e.getEndTime());
        dto.setStatus(e.getStatus());
        dto.setAdminId(e.getAdmin() != null ? e.getAdmin().getId() : null);
        return dto;
    }
}
