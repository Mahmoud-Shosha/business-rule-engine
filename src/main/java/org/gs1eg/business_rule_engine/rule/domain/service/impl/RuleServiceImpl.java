package org.gs1eg.business_rule_engine.rule.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.gs1eg.business_rule_engine.rule.domain.model.Rule;
import org.gs1eg.business_rule_engine.rule.domain.model.RuleType;
import org.gs1eg.business_rule_engine.rule.domain.repo.RuleRepo;
import org.gs1eg.business_rule_engine.rule.domain.service.RuleService;
import org.gs1eg.business_rule_engine.rule.domain.validator.RuleValidator;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
class RuleServiceImpl implements RuleService {

    private final RuleRepo repo;
    private final RuleValidator validator;

    @Override
    public Page<Rule> findAll(int pageNumber, int pageSize) {
        return repo.findAll(pageNumber, pageSize);
    }

    @Override
    public Optional<Rule> findById(UUID id) {
        return repo.findById(id);
    }

    @Override
    public Rule save(Rule rule) {
        return repo.save(rule);
    }

    @Override
    public Rule update(Rule rule) {
        return repo.update(rule);
    }

    @Override
    public void deleteById(UUID id) {
        repo.deleteById(id);
    }

    @Override
    public List<Rule> findByTypeOrderByPriorityAsc(RuleType type) {
        return repo.findByTypeOrderByPriorityAsc(type);
    }
}
