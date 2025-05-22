package com.resumechecker.resumeevaluator.service;




import com.resumechecker.resumeevaluator.dto.RuleDTO;
import com.resumechecker.resumeevaluator.model.Rule;
import com.resumechecker.resumeevaluator.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    public List<RuleDTO> getAllRules() {
        return ruleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RuleDTO getRuleById(Long id) {
        Optional<Rule> rule = ruleRepository.findById(id);
        return rule.map(this::convertToDTO).orElse(null);
    }

    public RuleDTO createRule(RuleDTO ruleDTO) {
        Rule rule = convertToEntity(ruleDTO);
        Rule savedRule = ruleRepository.save(rule);
        return convertToDTO(savedRule);
    }

    public RuleDTO updateRule(Long id, RuleDTO ruleDTO) {
        Optional<Rule> existingRule = ruleRepository.findById(id);
        if (existingRule.isPresent()) {
            Rule rule = existingRule.get();
            updateRuleFields(rule, ruleDTO);
            Rule updatedRule = ruleRepository.save(rule);
            return convertToDTO(updatedRule);
        }
        return null;
    }

    public boolean deleteRule(Long id) {
        if (ruleRepository.existsById(id)) {
            ruleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public RuleDTO toggleRuleStatus(Long id) {
        Optional<Rule> existingRule = ruleRepository.findById(id);
        if (existingRule.isPresent()) {
            Rule rule = existingRule.get();
            rule.setIsActive(!rule.getIsActive());
            Rule updatedRule = ruleRepository.save(rule);
            return convertToDTO(updatedRule);
        }
        return null;
    }

    public List<Rule> getActiveRules() {
        return ruleRepository.findByIsActiveTrue();
    }

    private RuleDTO convertToDTO(Rule rule) {
        RuleDTO dto = new RuleDTO();
        dto.setId(rule.getId());
        dto.setName(rule.getName());
        dto.setDescription(rule.getDescription());
        dto.setKeyword(rule.getKeyword());
        dto.setCategory(rule.getCategory());
        dto.setMarks(rule.getMarks());
        dto.setIsActive(rule.getIsActive());
        return dto;
    }

    private Rule convertToEntity(RuleDTO dto) {
        Rule rule = new Rule();
        updateRuleFields(rule, dto);
        return rule;
    }

    private void updateRuleFields(Rule rule, RuleDTO dto) {
        rule.setName(dto.getName());
        rule.setDescription(dto.getDescription());
        rule.setKeyword(dto.getKeyword());
        rule.setCategory(dto.getCategory());
        rule.setMarks(dto.getMarks());
        if (dto.getIsActive() != null) {
            rule.setIsActive(dto.getIsActive());
        }
    }
}