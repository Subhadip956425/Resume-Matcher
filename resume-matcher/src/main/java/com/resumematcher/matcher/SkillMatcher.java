package com.resumematcher.matcher;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Component
public class SkillMatcher {

    // Formula: (Matched JD Skills / Total JD Skills) × 100
    public Map<String, Object> match(List<String> resumeSkills, List<String> jdSkills) {
        List<Map<String, Object>> skillsAnalysis = new ArrayList<>();
        int matchedCount = 0;

        for (String jdSkill : jdSkills) {
            boolean present = containsIgnoreCase(resumeSkills, jdSkill);
            if (present) matchedCount++;

            Map<String, Object> entry = new HashMap<>();
            entry.put("skill", jdSkill);
            entry.put("presentInResume", present);
            skillsAnalysis.add(entry);
        }

        double score = jdSkills.isEmpty() ? 0.0
            : Math.round(((double) matchedCount / jdSkills.size()) * 100.0 * 10.0) / 10.0;

        Map<String, Object> result = new HashMap<>();
        result.put("skillsAnalysis", skillsAnalysis);
        result.put("matchingScore", score);
        result.put("matchedCount", matchedCount);
        result.put("totalJdSkills", jdSkills.size());

        return result;
    }

    // Case-insensitive comparison so "java" matches "Java"
    private boolean containsIgnoreCase(List<String> skills, String target) {
        return skills.stream()
            .anyMatch(s -> s.equalsIgnoreCase(target));
    }
}
