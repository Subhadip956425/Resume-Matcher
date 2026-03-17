package com.resumematcher.parser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResumeParser {

    private final SalaryExtractor salaryExtractor;
    private final ExperienceExtractor experienceExtractor;
    private final SkillExtractor skillExtractor;

    private static final Pattern NAME_PATTERN = Pattern.compile(
            "^([A-Z][a-z]+(?:\\s+[A-Z][a-z]+){1,3})$",
            Pattern.MULTILINE
    );

    public Map<String, Object> parse(MultipartFile file) throws IOException {
        String rawText = extractTextFromPdf(file);
        log.debug("Extracted raw text from PDF ({} chars)", rawText.length());

        Map<String, Object> result = new HashMap<>();
        result.put("name", extractName(rawText));
        result.put("salary", salaryExtractor.extract(rawText));
        result.put("yearOfExperience", experienceExtractor.extract(rawText));
        result.put("resumeSkills", skillExtractor.extract(rawText));
        result.put("rawText", rawText);

        return result;
    }

    private String extractTextFromPdf(MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        try (PDDocument document = Loader.loadPDF(fileBytes)) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            return stripper.getText(document);
        }
    }

    private String extractName(String text) {
        String[] lines = text.split("\\r?\\n");
        for (int i = 0; i < Math.min(20, lines.length); i++) {
            String line = lines[i].trim();
            if (line.isBlank()) continue;

            Matcher matcher = NAME_PATTERN.matcher(line);
            if (matcher.find()) {
                String candidate = matcher.group(1);
                if (!candidate.toLowerCase().matches(".*(resume|curriculum|vitae|address|email|phone|objective|summary).*")) {
                    return candidate;
                }
            }
        }

        for (String line : lines) {
            String trimmed = line.trim();
            if (!trimmed.isBlank() && trimmed.length() > 2 && trimmed.length() < 60) {
                return trimmed;
            }
        }

        return "Unknown";
    }
}
