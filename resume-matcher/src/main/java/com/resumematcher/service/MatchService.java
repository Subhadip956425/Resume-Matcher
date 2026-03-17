package com.resumematcher.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resumematcher.dto.MatchOutputDTO;
import com.resumematcher.dto.MatchedJobDTO;
import com.resumematcher.dto.SkillAnalysisDTO;
import com.resumematcher.matcher.SkillMatcher;
import com.resumematcher.model.JobDescription;
import com.resumematcher.model.MatchResult;
import com.resumematcher.model.Resume;
import com.resumematcher.repository.MatchResultRepository;
import com.resumematcher.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatchService {

    private final ResumeRepository resumeRepository;
    private final JdService jdService;
    private final SkillMatcher skillMatcher;
    private final MatchResultRepository matchResultRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public MatchOutputDTO matchResumeAgainstAllJds(Long resumeId) throws JsonProcessingException {
        Resume resume = resumeRepository.findById(resumeId)
            .orElseThrow(() -> new RuntimeException("Resume not found with id: " + resumeId));

        List<String> resumeSkills = parseSkills(resume.getSkills());
        List<JobDescription> allJds = jdService.getAllRaw();

        // Delete old match results for this resume before re running
        matchResultRepository.deleteByResume(resume);

        List<MatchedJobDTO> matchedJobs = new ArrayList<>();

        for (JobDescription jd : allJds) {
            List<String> jdSkills = jdService.parseSkills(jd.getAllSkills());

            Map<String, Object> matchResult = skillMatcher.match(resumeSkills, jdSkills);

            List<Map<String, Object>> rawAnalysis = (List<Map<String, Object>>) matchResult.get("skillsAnalysis");
            List<SkillAnalysisDTO> skillsAnalysis = rawAnalysis.stream()
                .map(entry -> SkillAnalysisDTO.builder()
                    .skill((String) entry.get("skill"))
                    .presentInResume((Boolean) entry.get("presentInResume"))
                    .build())
                .toList();

            double score = (Double) matchResult.get("matchingScore");
            int matchedCount = (Integer) matchResult.get("matchedCount");
            int totalJdSkills = (Integer) matchResult.get("totalJdSkills");

            // Persist the result to the database
            MatchResult savedResult = MatchResult.builder()
                .resume(resume)
                .jobDescription(jd)
                .matchingScore(score)
                .matchedSkillsCount(matchedCount)
                .totalJdSkills(totalJdSkills)
                .skillsAnalysisJson(objectMapper.writeValueAsString(skillsAnalysis))
                .build();
            matchResultRepository.save(savedResult);

            matchedJobs.add(MatchedJobDTO.builder()
                .jobId(jd.getId())
                .role(jd.getRole())
                .aboutRole(jd.getAboutRole())
                .salary(jd.getSalary())
                .yearOfExperience(jd.getYearOfExperience())
                .skillsAnalysis(skillsAnalysis)
                .matchingScore(score)
                .matchedCount(matchedCount)
                .totalJdSkills(totalJdSkills)
                .build());
        }

        // Sort by descending
        matchedJobs.sort((a, b) -> Double.compare(b.getMatchingScore(), a.getMatchingScore()));

        log.info("Match complete for resumeId: {} against {} JDs", resumeId, allJds.size());

        return MatchOutputDTO.builder()
            .resumeId(resumeId)
            .name(resume.getName())
            .salary(resume.getSalary())
            .yearOfExperience(resume.getYearOfExperience())
            .resumeSkills(resumeSkills)
            .matchingJobs(matchedJobs)
            .build();
    }

    private List<String> parseSkills(String skillsStr) {
        if (skillsStr == null || skillsStr.isBlank()) return List.of();
        return List.of(skillsStr.split(","));
    }
}
