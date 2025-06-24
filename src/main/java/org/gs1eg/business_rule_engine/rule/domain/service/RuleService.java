package org.gs1eg.business_rule_engine.rule.domain.service;

import org.gs1eg.business_rule_engine.rule.domain.model.Rule;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface RuleService {

    Page<Rule> findAll(int pageNumber, int pageSize);

    Optional<Rule> findById(UUID id);

    Rule save(Rule rule);

    Rule update(Rule rule);

    void deleteById(UUID id);

}
