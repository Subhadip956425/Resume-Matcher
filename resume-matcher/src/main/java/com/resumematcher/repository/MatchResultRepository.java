package com.resumematcher.repository;

import com.resumematcher.model.MatchResult;
import com.resumematcher.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchResultRepository extends JpaRepository<MatchResult, Long> {

    // Fetch all match results for a specific resume
    List<MatchResult> findByResume(Resume resume);

    // Delete old results before rerunning a match
    void deleteByResume(Resume resume);
}
