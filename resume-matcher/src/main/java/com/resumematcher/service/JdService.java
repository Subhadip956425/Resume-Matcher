package com.resumematcher.service;

import com.resumematcher.dto.JdRequestDTO;
import com.resumematcher.dto.JdResponseDTO;
import com.resumematcher.model.JobDescription;
import com.resumematcher.parser.JdParser;
import com.resumematcher.repository.JobDescriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class JdService {

    private final JdParser jdParser;
    private final JobDescriptionRepository jdRepository;

    public JdResponseDTO addJd(JdRequestDTO request) {
        log.info("Parsing JD for role: {}", request.getRole());

        Map<String, Object> parsed = jdParser.parse(request.getJdText());

        List<String> requiredSkills = (List<String>) parsed.get("requiredSkills");
        List<String> optionalSkills = (List<String>) parsed.get("optionalSkills");
        List<String> allSkills = (List<String>) parsed.get("allSkills");

        JobDescription jd = JobDescription.builder()
            .role(request.getRole())
            .rawText(request.getJdText())
            .aboutRole((String) parsed.get("aboutRole"))
            .salary((String) parsed.get("salary"))
            .yearOfExperience((Double) parsed.get("yearOfExperience"))
            .requiredSkills(String.join(",", requiredSkills))
            .optionalSkills(String.join(",", optionalSkills))
            .allSkills(String.join(",", allSkills))
            .build();

        JobDescription saved = jdRepository.save(jd);
        log.info("JD saved with ID: {}", saved.getId());

        return toDTO(saved, requiredSkills, optionalSkills);
    }

    public List<JdResponseDTO> getAllJds() {
        return jdRepository.findAll()
            .stream()
            .map(jd -> toDTO(jd, parseSkills(jd.getRequiredSkills()), parseSkills(jd.getOptionalSkills())))
            .toList();
    }

    public JdResponseDTO getById(Long id) {
        JobDescription jd = jdRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("JD not found with id: " + id));
        return toDTO(jd, parseSkills(jd.getRequiredSkills()), parseSkills(jd.getOptionalSkills()));
    }

    // Used internally by MatchService
    public JobDescription getRawById(Long id) {
        return jdRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("JD not found with id: " + id));
    }

    public List<JobDescription> getAllRaw() {
        return jdRepository.findAll();
    }

    public List<String> parseSkills(String skillsStr) {
        if (skillsStr == null || skillsStr.isBlank()) return List.of();
        return Arrays.asList(skillsStr.split(","));
    }

    private JdResponseDTO toDTO(JobDescription jd, List<String> required, List<String> optional) {
        return JdResponseDTO.builder()
            .id(jd.getId())
            .role(jd.getRole())
            .aboutRole(jd.getAboutRole())
            .salary(jd.getSalary())
            .yearOfExperience(jd.getYearOfExperience())
            .requiredSkills(required)
            .optionalSkills(optional)
            .createdAt(jd.getCreatedAt() != null ? jd.getCreatedAt().toString() : null)
            .build();
    }
}
