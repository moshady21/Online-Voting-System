package com.votingsystem.controller;

import com.votingsystem.dto.election.*;
import com.votingsystem.dto.results.CandidateResultResponse;
import com.votingsystem.service.ElectionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/admin/elections")
public class ElectionController {

    private final ElectionService electionService;
    public ElectionController(ElectionService electionService) { this.electionService = electionService; }

    @PostMapping
    public ResponseEntity<ElectionResponse> create(@Valid @RequestBody ElectionCreateRequest req) {
        ElectionResponse resp = electionService.create(req);
        return ResponseEntity.created(URI.create("/api/admin/elections/" + resp.getId())).body(resp);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ElectionResponse> update(@PathVariable Long id, @Valid @RequestBody ElectionUpdateRequest req) {
        return ResponseEntity.ok(electionService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        electionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElectionResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(electionService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ElectionResponse>> list() {
        return ResponseEntity.ok(electionService.listAll());
    }

    // Results for an election
    @GetMapping("/{id}/results")
    public ResponseEntity<List<CandidateResultResponse>> results(@PathVariable Long id) {
        return ResponseEntity.ok(electionService.getResults(id));
    }
}
