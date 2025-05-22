package com.resumechecker.resumeevaluator.controller;

import com.resumechecker.resumeevaluator.dto.RuleDTO;
import com.resumechecker.resumeevaluator.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
@CrossOrigin(origins = "http://192.168.80.23:3000")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @GetMapping
    public ResponseEntity<List<RuleDTO>> getAllRules() {
        List<RuleDTO> rules = ruleService.getAllRules();
        return new ResponseEntity<>(rules, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleDTO> getRuleById(@PathVariable Long id) {
        RuleDTO rule = ruleService.getRuleById(id);
        if (rule != null) {
            return new ResponseEntity<>(rule, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<RuleDTO> createRule(@RequestBody RuleDTO ruleDTO) {
        RuleDTO createdRule = ruleService.createRule(ruleDTO);
        return new ResponseEntity<>(createdRule, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RuleDTO> updateRule(@PathVariable Long id, @RequestBody RuleDTO ruleDTO) {
        RuleDTO updatedRule = ruleService.updateRule(id, ruleDTO);
        if (updatedRule != null) {
            return new ResponseEntity<>(updatedRule, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        boolean deleted = ruleService.deleteRule(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<RuleDTO> toggleRuleStatus(@PathVariable Long id) {
        RuleDTO updatedRule = ruleService.toggleRuleStatus(id);
        if (updatedRule != null) {
            return new ResponseEntity<>(updatedRule, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}