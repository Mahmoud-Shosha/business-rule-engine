package org.gs1eg.business_rule_engine.rule.infrastructure.persistence.repo;

import org.gs1eg.business_rule_engine.rule.domain.model.RuleType;
import org.gs1eg.business_rule_engine.rule.infrastructure.persistence.entity.RuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
interface JpaRuleRepo extends JpaRepository<RuleEntity, UUID> {

    List<RuleEntity> findByTypeOrderByPriorityAsc(RuleType type);

}