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
public class ResumeResponseDTO {

    private Long id;
    private String name;
    private String salary;
    private Double yearOfExperience;
    private List<String> resumeSkills;
    private String fileName;
    private String uploadedAt;
}
