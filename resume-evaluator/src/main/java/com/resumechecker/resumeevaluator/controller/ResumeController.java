package com.resumechecker.resumeevaluator.controller;



import com.resumechecker.resumeevaluator.dto.ResumeReportDTO;
import com.resumechecker.resumeevaluator.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/resumes")
@CrossOrigin(origins = "http://192.168.80.23:3000")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadResume(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return new ResponseEntity<>("Please select a file to upload", HttpStatus.BAD_REQUEST);
            }

            if (!file.getContentType().equals("application/pdf")) {
                return new ResponseEntity<>("Only PDF files are supported", HttpStatus.BAD_REQUEST);
            }

            ResumeReportDTO report = resumeService.uploadAndEvaluateResume(file);
            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload and process file: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<ResumeReportDTO>> getAllResumes() {
        List<ResumeReportDTO> reports = resumeService.getAllResumes();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeReportDTO> getResumeById(@PathVariable Long id) {
        ResumeReportDTO report = resumeService.getResumeById(id);
        if (report != null) {
            return new ResponseEntity<>(report, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}