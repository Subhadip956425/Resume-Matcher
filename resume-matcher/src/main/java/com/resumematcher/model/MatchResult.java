package com.resumematcher.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "match_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jd_id")
    private JobDescription jobDescription;

    private Double matchingScore;

    private Integer matchedSkillsCount;

    private Integer totalJdSkills;

    @Column(columnDefinition = "TEXT")
    private String skillsAnalysisJson;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
