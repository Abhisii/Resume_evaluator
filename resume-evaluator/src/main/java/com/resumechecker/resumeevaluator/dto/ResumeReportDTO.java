package com.resumechecker.resumeevaluator.dto;


import java.time.LocalDateTime;
import java.util.List;

public class ResumeReportDTO {
    private Long id;
    private String fileName;
    private Integer totalMarks;
    private Double percentage;
    private LocalDateTime uploadTime;
    private List<RuleMatchDTO> ruleMatches;

    public static class RuleMatchDTO {
        private String ruleName;
        private String keyword;
        private Boolean matched;
        private Integer score;


        // Getters and Setters
        public String getRuleName() {
            return ruleName;
        }

        public void setRuleName(String ruleName) {
            this.ruleName = ruleName;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public Boolean getMatched() {
            return matched;
        }

        public void setMatched(Boolean matched) {
            this.matched = matched;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public List<RuleMatchDTO> getRuleMatches() {
        return ruleMatches;
    }

    public void setRuleMatches(List<RuleMatchDTO> ruleMatches) {
        this.ruleMatches = ruleMatches;
    }

}