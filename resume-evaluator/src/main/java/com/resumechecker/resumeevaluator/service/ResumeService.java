package com.resumechecker.resumeevaluator.service;


import com.resumechecker.resumeevaluator.dto.ResumeReportDTO;
import com.resumechecker.resumeevaluator.model.Resume;
import com.resumechecker.resumeevaluator.model.ResumeRuleScore;
import com.resumechecker.resumeevaluator.model.Rule;
import com.resumechecker.resumeevaluator.repository.ResumeRepository;
import com.resumechecker.resumeevaluator.repository.ResumeRuleScoreRepository;
import com.resumechecker.resumeevaluator.utility.PdfParserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private ResumeRuleScoreRepository resumeRuleScoreRepository;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private PdfParserUtil pdfParserUtil;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    public ResumeReportDTO uploadAndEvaluateResume(MultipartFile file) throws IOException {
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

        // Save file to disk
        Path filePath = uploadPath.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Extract text from PDF
        String resumeText = pdfParserUtil.extractTextFromPdf(file);

        // Get active rules
        List<Rule> activeRules = ruleService.getActiveRules();

        // Create resume entry
        Resume resume = new Resume();
        resume.setFileName(originalFilename);
        resume.setFilePath(filePath.toString());
        resume.setActiveRulesApplied(activeRules.size());

        // Evaluate resume against rules
        Integer totalMarks = 0;
        Integer totalPossibleMarks = 0;
        List<ResumeRuleScore> ruleScores = new ArrayList<>();

        for (Rule rule : activeRules) {
            boolean matched = pdfParserUtil.containsKeyword(resumeText, rule.getKeyword());
            int score = matched ? rule.getMarks() : 0;

            totalPossibleMarks += rule.getMarks();
            totalMarks += score;

            ResumeRuleScore ruleScore = new ResumeRuleScore();
            ruleScore.setResume(resume);
            ruleScore.setRule(rule);
            ruleScore.setMatched(matched);
            ruleScore.setScore(score);

            ruleScores.add(ruleScore);
        }

        // Calculate percentage
        double percentage = totalPossibleMarks > 0 ?
                ((double) totalMarks / totalPossibleMarks) * 100 : 0;

        resume.setTotalMarks(totalMarks);
        resume.setPercentage(percentage);

        // Save resume and scores
        Resume savedResume = resumeRepository.save(resume);

        for (ResumeRuleScore ruleScore : ruleScores) {
            resumeRuleScoreRepository.save(ruleScore);
        }

        // Create report DTO
        return createReportDTO(savedResume, ruleScores);
    }

    public List<ResumeReportDTO> getAllResumes() {
        List<Resume> resumes = resumeRepository.findAll();
        return resumes.stream()
                .map(resume -> {
                    List<ResumeRuleScore> scores = resumeRuleScoreRepository.findByResume(resume);
                    return createReportDTO(resume, scores);
                })
                .collect(Collectors.toList());
    }

    public ResumeReportDTO getResumeById(Long id) {
        Optional<Resume> resumeOpt = resumeRepository.findById(id);
        if (resumeOpt.isPresent()) {
            Resume resume = resumeOpt.get();
            List<ResumeRuleScore> scores = resumeRuleScoreRepository.findByResume(resume);
            return createReportDTO(resume, scores);
        }
        return null;
    }

    private ResumeReportDTO createReportDTO(Resume resume, List<ResumeRuleScore> scores) {
        ResumeReportDTO reportDTO = new ResumeReportDTO();
        reportDTO.setId(resume.getId());
        reportDTO.setFileName(resume.getFileName());
        reportDTO.setTotalMarks(resume.getTotalMarks());
        reportDTO.setPercentage(resume.getPercentage());
        reportDTO.setUploadTime(resume.getUploadTime());

        List<ResumeReportDTO.RuleMatchDTO> ruleMatches = scores.stream()
                .map(score -> {
                    ResumeReportDTO.RuleMatchDTO ruleMatch = new ResumeReportDTO.RuleMatchDTO();
                    ruleMatch.setRuleName(score.getRule().getName());
                    ruleMatch.setKeyword(score.getRule().getKeyword());
                    ruleMatch.setMatched(score.getMatched());
                    ruleMatch.setScore(score.getScore());
                    return ruleMatch;
                })
                .collect(Collectors.toList());

        reportDTO.setRuleMatches(ruleMatches);
        return reportDTO;
    }
}