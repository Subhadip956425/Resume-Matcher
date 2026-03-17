package com.resumematcher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchedJobDTO {

    private Long jobId;
    private String role;
    private String aboutRole;
    private String salary;
    private Double yearOfExperience;
    private List<SkillAnalysisDTO> skillsAnalysis;
    private Double matchingScore;
    private int matchedCount;
    private int totalJdSkills;
}
