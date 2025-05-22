package com.resumechecker.resumeevaluator.repository;

import com.resumechecker.resumeevaluator.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
}