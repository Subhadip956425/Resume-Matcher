package com.resumematcher.parser;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExperienceExtractor {

    private static final Pattern DIRECT_YEARS = Pattern.compile(
        "(\\d+(?:\\.\\d+)?)\\+?\\s*(?:-\\s*\\d+)?\\s*(?:years?|yrs?)\\s*(?:of\\s+)?(?:experience|exp)",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern FRESHER = Pattern.compile(
        "\\b(fresher|entry.?level|0\\s*years?)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern DATE_RANGE = Pattern.compile(
        "((?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)[a-z]*\\.?\\s+)?\\s*(\\d{4})\\s*[-–—]\\s*((?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)[a-z]*\\.?\\s+)?\\s*(\\d{4}|[Pp]resent|[Cc]urrent|[Tt]ill\\s+[Dd]ate)",
        Pattern.CASE_INSENSITIVE
    );

    public Double extract(String text) {
        if (text == null || text.isBlank()) return null;

        Matcher directMatcher = DIRECT_YEARS.matcher(text);
        if (directMatcher.find()) {
            return Double.parseDouble(directMatcher.group(1));
        }

        if (FRESHER.matcher(text).find()) {
            return 0.0;
        }

        // Fall back to calculating from date ranges
        return calculateFromDateRanges(text);
    }

    private Double calculateFromDateRanges(String text) {
        Matcher rangeMatcher = DATE_RANGE.matcher(text);
        double totalMonths = 0;

        while (rangeMatcher.find()) {
            String startMonthStr = rangeMatcher.group(1);
            String startYearStr = rangeMatcher.group(2);
            String endMonthStr = rangeMatcher.group(3);
            String endYearStr = rangeMatcher.group(4);

            LocalDate startDate = parseDate(startMonthStr, startYearStr);
            LocalDate endDate = parseEndDate(endMonthStr, endYearStr);

            if (startDate != null && endDate != null && endDate.isAfter(startDate)) {
                totalMonths += ChronoUnit.MONTHS.between(startDate, endDate);
            }
        }

        if (totalMonths > 0) {
            return Math.round((totalMonths / 12.0) * 10.0) / 10.0;
        }

        return null;
    }

    private LocalDate parseDate(String monthStr, String yearStr) {
        try {
            int year = Integer.parseInt(yearStr.trim());
            if (monthStr != null && !monthStr.isBlank()) {
                String cleaned = monthStr.trim().replaceAll("\\.", "");
                String month = cleaned.substring(0, Math.min(3, cleaned.length()));
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM yyyy", Locale.ENGLISH);
                return LocalDate.parse(month + " " + year, fmt).withDayOfMonth(1);
            }
            return LocalDate.of(year, 1, 1);
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDate parseEndDate(String monthStr, String yearStr) {
        if (yearStr == null) return null;
        String lower = yearStr.toLowerCase().trim();
        if (lower.contains("present") || lower.contains("current") || lower.contains("till")) {
            return LocalDate.now();
        }
        return parseDate(monthStr, yearStr);
    }
}
