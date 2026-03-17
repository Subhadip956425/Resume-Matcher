package com.resumematcher.controller;

import com.resumematcher.dto.ResumeResponseDTO;
import com.resumematcher.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    // Upload a PDF resume, parse it, save to DB and return parsed result
    @PostMapping("/upload")
    public ResponseEntity<ResumeResponseDTO> uploadResume(
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.equalsIgnoreCase("application/pdf")) {
            log.warn("Rejected upload - not a PDF: {}", contentType);
            return ResponseEntity.badRequest().build();
        }

        try {
            ResumeResponseDTO response = resumeService.uploadAndParse(file);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            log.error("Failed to parse resume: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // Fetch a previously uploaded and parsed resume by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResumeResponseDTO> getResume(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(resumeService.getById(id));
        } catch (RuntimeException e) {
            log.warn("Resume not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
