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
public class JdResponseDTO {

    private Long id;
    private String role;
    private String aboutRole;
    private String salary;
    private Double yearOfExperience;
    private List<String> requiredSkills;
    private List<String> optionalSkills;
    private String createdAt;
}
