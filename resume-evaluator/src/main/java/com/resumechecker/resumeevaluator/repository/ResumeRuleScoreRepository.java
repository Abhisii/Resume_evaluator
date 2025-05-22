package com.resumechecker.resumeevaluator.repository;


import com.resumechecker.resumeevaluator.model.Resume;
import com.resumechecker.resumeevaluator.model.ResumeRuleScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResumeRuleScoreRepository extends JpaRepository<ResumeRuleScore, Long> {
    List<ResumeRuleScore> findByResume(Resume resume);
}
