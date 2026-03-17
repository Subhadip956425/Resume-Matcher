package com.resumematcher.controller;

import com.resumematcher.dto.JdRequestDTO;
import com.resumematcher.dto.JdResponseDTO;
import com.resumematcher.service.JdService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/jd")
@RequiredArgsConstructor
public class JdController {

    private final JdService jdService;

    // Add a new JD
    @PostMapping
    public ResponseEntity<JdResponseDTO> addJd(@Valid @RequestBody JdRequestDTO request) {
        JdResponseDTO response = jdService.addJd(request);
        return ResponseEntity.ok(response);
    }

    // Get all stored Job Descriptions
    @GetMapping
    public ResponseEntity<List<JdResponseDTO>> getAllJds() {
        return ResponseEntity.ok(jdService.getAllJds());
    }

    // Get a single Job Description by ID
    @GetMapping("/{id}")
    public ResponseEntity<JdResponseDTO> getJdById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(jdService.getById(id));
        } catch (RuntimeException e) {
            log.warn("JD not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
