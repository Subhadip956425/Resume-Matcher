package com.resumematcher.parser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class JdParser {

    private final SalaryExtractor salaryExtractor;
    private final ExperienceExtractor experienceExtractor;
    private final SkillExtractor skillExtractor;

    private static final Pattern REQUIRED_SECTION = Pattern.compile(
        "(?i)(required|must.have|mandatory|minimum qualifications?|basic qualifications?|what you need)",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern OPTIONAL_SECTION = Pattern.compile(
        "(?i)(good.to.have|optional|preferred|desired|bonus|nice.to.have|plus|additional)",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern ABOUT_SECTION = Pattern.compile(
        "(?i)(about\\s+(?:the\\s+)?role|job\\s+description|position\\s+overview|the\\s+opportunity|overview|responsibilities|what\\s+you.ll\\s+do)",
        Pattern.CASE_INSENSITIVE
    );

    public Map<String, Object> parse(String jdText) {
        if (jdText == null || jdText.isBlank()) {
            return new HashMap<>();
        }

        Map<String, Object> result = new HashMap<>();

        String[] sections = splitIntoSections(jdText);
        String requiredSection = sections[0];
        String optionalSection = sections[1];

        List<String> requiredSkills = skillExtractor.extract(requiredSection);
        List<String> optionalSkills = skillExtractor.extract(optionalSection);

        optionalSkills.removeAll(requiredSkills);

        result.put("salary", salaryExtractor.extract(jdText));
        result.put("yearOfExperience", experienceExtractor.extract(jdText));
        result.put("requiredSkills", requiredSkills);
        result.put("optionalSkills", optionalSkills);
        result.put("allSkills", mergeSkills(requiredSkills, optionalSkills));
        result.put("aboutRole", extractAboutRole(jdText));

        log.debug("JD parsed - required skills: {}, optional: {}", requiredSkills.size(), optionalSkills.size());
        return result;
    }

    private String[] splitIntoSections(String text) {
        String requiredSection = text;
        String optionalSection = "";

        Matcher optionalMatcher = OPTIONAL_SECTION.matcher(text);
        if (optionalMatcher.find()) {
            int splitIndex = optionalMatcher.start();
            requiredSection = text.substring(0, splitIndex);
            optionalSection = text.substring(splitIndex);
        }

        return new String[]{requiredSection, optionalSection};
    }

    private List<String> mergeSkills(List<String> required, List<String> optional) {
        List<String> merged = new ArrayList<>(required);
        for (String skill : optional) {
            if (!merged.contains(skill)) {
                merged.add(skill);
            }
        }
        return merged;
    }

    private String extractAboutRole(String text) {
        Matcher aboutMatcher = ABOUT_SECTION.matcher(text);

        if (aboutMatcher.find()) {
            int start = aboutMatcher.end();

            int end = Math.min(start + 500, text.length());
            String snippet = text.substring(start, end).trim();

            snippet = snippet.replaceAll("[•\\-\\*]", "").replaceAll("\\s+", " ").trim();

            int dotIndex = snippet.indexOf('.', 100);
            if (dotIndex > 0 && dotIndex < 400) {
                snippet = snippet.substring(0, dotIndex + 1).trim();
            }

            return snippet;
        }

        String fallback = text.replaceAll("\\s+", " ").trim();
        return fallback.substring(0, Math.min(300, fallback.length()));
    }
}
