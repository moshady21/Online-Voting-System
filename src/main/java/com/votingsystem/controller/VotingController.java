package com.votingsystem.controller;

import com.votingsystem.dto.vote.VoteRequestDTO;
import com.votingsystem.dto.vote.VoteResponseDTO;
import com.votingsystem.exception.VotingClosedException;
import com.votingsystem.service.VotingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votes")
public class VotingController {

    private final VotingService votingService;

    public VotingController(VotingService votingService) {
        this.votingService = votingService;
    }

    /** Cast a vote (only once per election) */
    @PostMapping
    public ResponseEntity<VoteResponseDTO> castVote(@RequestBody VoteRequestDTO request) {
        try{
            VoteResponseDTO response = votingService.castVote(request);
            return ResponseEntity.ok(response);
        }
        catch (VotingClosedException e){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new VoteResponseDTO(e.getMessage()));
        }
    }
}
