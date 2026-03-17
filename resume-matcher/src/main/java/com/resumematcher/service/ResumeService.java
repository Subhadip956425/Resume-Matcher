package com.resumematcher.service;

import com.resumematcher.dto.ResumeResponseDTO;
import com.resumematcher.model.Resume;
import com.resumematcher.parser.ResumeParser;
import com.resumematcher.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeParser resumeParser;
    private final ResumeRepository resumeRepository;

    public ResumeResponseDTO uploadAndParse(MultipartFile file) throws IOException {
        log.info("Parsing resume: {}", file.getOriginalFilename());

        Map<String, Object> parsed = resumeParser.parse(file);

        List<String> skills = (List<String>) parsed.get("resumeSkills");

        Resume resume = Resume.builder()
            .name((String) parsed.get("name"))
            .salary((String) parsed.get("salary"))
            .yearOfExperience((Double) parsed.get("yearOfExperience"))
            .skills(String.join(",", skills))
            .rawText((String) parsed.get("rawText"))
            .fileName(file.getOriginalFilename())
            .build();

        Resume saved = resumeRepository.save(resume);
        log.info("Resume saved with ID: {}", saved.getId());

        return toDTO(saved, skills);
    }

    public ResumeResponseDTO getById(Long id) {
        Resume resume = resumeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Resume not found with id: " + id));
        return toDTO(resume, parseSkills(resume.getSkills()));
    }

    // Helper: convert comma separated skills string back to list
    public List<String> parseSkills(String skillsStr) {
        if (skillsStr == null || skillsStr.isBlank()) return List.of();
        return Arrays.asList(skillsStr.split(","));
    }

    private ResumeResponseDTO toDTO(Resume resume, List<String> skills) {
        return ResumeResponseDTO.builder()
            .id(resume.getId())
            .name(resume.getName())
            .salary(resume.getSalary())
            .yearOfExperience(resume.getYearOfExperience())
            .resumeSkills(skills)
            .fileName(resume.getFileName())
            .uploadedAt(resume.getUploadedAt() != null ? resume.getUploadedAt().toString() : null)
            .build();
    }
}
