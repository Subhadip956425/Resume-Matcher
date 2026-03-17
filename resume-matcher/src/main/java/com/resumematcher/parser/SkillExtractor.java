package com.resumematcher.parser;

import com.resumematcher.data.SkillsDictionary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class SkillExtractor {

    private final SkillsDictionary skillsDictionary;

    public List<String> extract(String text) {
        if (text == null || text.isBlank()) return new ArrayList<>();

        List<String> foundSkills = new ArrayList<>();

        for (String skill : skillsDictionary.getAllSkills()) {
            String escaped = Pattern.quote(skill);
            Pattern pattern = Pattern.compile(
                "(?i)(?<![a-zA-Z0-9])" + escaped + "(?![a-zA-Z0-9])"
            );

            if (pattern.matcher(text).find()) {
                foundSkills.add(skill);
            }
        }

        return foundSkills;
    }
}
