package org.gs1eg.business_rule_engine.rule.domain.repo;

import org.gs1eg.business_rule_engine.rule.domain.model.Rule;
import org.gs1eg.business_rule_engine.rule.domain.model.RuleType;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RuleRepo {

    Page<Rule> findAll(int pageNumber, int pageSize);

    Optional<Rule> findById(UUID id);

    Rule save(Rule rule);

    Rule update(Rule rule);

    void deleteById(UUID id);

    List<Rule> findByTypeOrderByPriorityAsc(RuleType type);
}
