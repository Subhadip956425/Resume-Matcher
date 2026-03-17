package com.resumematcher.parser;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SalaryExtractor {

    private static final Pattern[] SALARY_PATTERNS = {

        Pattern.compile("(?:CTC|Salary|Package|compensation)?\\s*:?\\s*₹?\\s*(\\d+(?:\\.\\d+)?(?:\\s*-\\s*\\d+(?:\\.\\d+)?)?)\\s*LPA",
            Pattern.CASE_INSENSITIVE),

        Pattern.compile("\\$([\\d,]+(?:\\.\\d+)?)\\s*(?:-|to)\\s*\\$([\\d,]+(?:\\.\\d+)?)",
            Pattern.CASE_INSENSITIVE),

        Pattern.compile("\\$([\\d,]+(?:\\.\\d+)?)\\s*(?:per year|annually|/year|/yr)?",
            Pattern.CASE_INSENSITIVE),

        Pattern.compile("\\$([\\d,]+(?:\\.\\d+)?)\\s*/\\s*hour",
            Pattern.CASE_INSENSITIVE),

        Pattern.compile("(?:compensation|salary|pay|range).*?(\\d{4,6})\\s*(?:-|to)\\s*(\\d{4,6})",
            Pattern.CASE_INSENSITIVE)
    };

    public String extract(String text) {
        if (text == null || text.isBlank()) return null;

        for (Pattern pattern : SALARY_PATTERNS) {
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return buildSalaryString(matcher);
            }
        }
        return null;
    }

    private String buildSalaryString(Matcher matcher) {
        try {
            String group1 = matcher.group(1);
            String group2 = matcher.group(2);
            if (group2 != null) {
                return group1.trim() + " - " + group2.trim();
            }
            return group1.trim();
        } catch (Exception e) {
            return matcher.group(0).trim();
        }
    }
}
