package com.votingsystem.controller;

import com.votingsystem.dto.candidate.CandidateCreateRequest;
import com.votingsystem.dto.candidate.CandidateResponse;
import com.votingsystem.service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/admin/candidates")
public class CandidateController {

    private final CandidateService candidateService;
    public CandidateController(CandidateService candidateService) { this.candidateService = candidateService; }

    @PostMapping
    public ResponseEntity<CandidateResponse> create(@Valid @RequestBody CandidateCreateRequest req) {
        CandidateResponse resp = candidateService.create(req);
        return ResponseEntity.created(URI.create("/api/admin/candidates/" + resp.getId())).body(resp);
    }

    @GetMapping("/election/{electionId}")
    public ResponseEntity<List<CandidateResponse>> listByElection(@PathVariable Long electionId) {
        return ResponseEntity.ok(candidateService.listByElection(electionId));
    }

    @DeleteMapping("/{candidateId}")
    public ResponseEntity<Void> delete(@PathVariable Long candidateId) {
        candidateService.delete(candidateId);
        return ResponseEntity.noContent().build();
    }
}
