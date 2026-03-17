package com.resumematcher.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.resumematcher.dto.MatchOutputDTO;
import com.resumematcher.service.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    // Run matching for a resume against all stored JDs & Returns sorted results
    @PostMapping("/{resumeId}")
    public ResponseEntity<MatchOutputDTO> matchResume(@PathVariable Long resumeId) {
        try {
            MatchOutputDTO result = matchService.matchResumeAgainstAllJds(resumeId);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            log.warn("Match failed - resume not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (JsonProcessingException e) {
            log.error("JSON serialization error during match: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
