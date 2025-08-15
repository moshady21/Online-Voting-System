package com.votingsystem.controller;

import com.votingsystem.dto.voter.AssignVotersRequest;
import com.votingsystem.entity.Voter;
import com.votingsystem.service.VoterAdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/voters")
public class VoterAdminController {

    private final VoterAdminService voterAdminService;
    public VoterAdminController(VoterAdminService voterAdminService) { this.voterAdminService = voterAdminService; }

    @PostMapping("/assign")
    public ResponseEntity<Map<String, Object>> assign(@Valid @RequestBody AssignVotersRequest req) {
        return ResponseEntity.ok(voterAdminService.assignVotersToElection(req));
    }

    @GetMapping("/eligible/{electionId}")
    public ResponseEntity<List<Voter>> eligible(@PathVariable Long electionId) {
        return ResponseEntity.ok(voterAdminService.listEligibleVotersForElection(electionId));
    }

    @GetMapping("/unassigned")
    public ResponseEntity<List<Voter>> unassignedByCity(@RequestParam String city) {
        return ResponseEntity.ok(voterAdminService.listUnassignedByCity(city));
    }
}
