package com.votingsystem.dto.voter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class AssignVotersRequest {
    @NotNull private Long electionId;
    @NotEmpty private List<Long> voterIds;

    public Long getElectionId() {
        return electionId;
    }

    public void setElectionId(Long electionId) {
        this.electionId = electionId;
    }

    public List<Long> getVoterIds() {
        return voterIds;
    }

    public void setVoterIds(List<Long> voterIds) {
        this.voterIds = voterIds;
    }
}
