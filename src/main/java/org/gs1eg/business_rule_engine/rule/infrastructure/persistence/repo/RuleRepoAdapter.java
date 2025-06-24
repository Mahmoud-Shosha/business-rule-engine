package org.gs1eg.business_rule_engine.rule.infrastructure.persistence.repo;

import lombok.RequiredArgsConstructor;
import org.gs1eg.business_rule_engine.rule.domain.model.Rule;
import org.gs1eg.business_rule_engine.rule.domain.model.RuleType;
import org.gs1eg.business_rule_engine.rule.domain.repo.RuleRepo;
import org.gs1eg.business_rule_engine.rule.infrastructure.persistence.entity.RuleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Repository
class RuleRepoAdapter implements RuleRepo {

    private final JpaRuleRepo repo;
    private final ModelMapper mapper;

    @Override
    public Page<Rule> findAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC, "name");
        Page<RuleEntity> ruleEntityList = repo.findAll(pageRequest);
        return ruleEntityList.map(entity -> mapper.map(entity, Rule.class));
    }

    @Override
    public Optional<Rule> findById(UUID id) {
        Optional<RuleEntity> ruleEntity = repo.findById(id);
        return ruleEntity.map(entity -> mapper.map(ruleEntity, Rule.class));
    }

    @Override
    public Rule save(Rule rule) {
        RuleEntity entity = mapper.map(rule, RuleEntity.class);
        repo.save(entity);
        return mapper.map(entity, Rule.class);
    }

    @Override
    public Rule update(Rule rule) {
        RuleEntity entity = mapper.map(rule, RuleEntity.class);
        repo.save(entity);
        return mapper.map(entity, Rule.class);
    }

    @Override
    public void deleteById(UUID id) {
        repo.deleteById(id);
    }

    @Override
    public List<Rule> findByTypeOrderByPriorityAsc(RuleType type) {
        List<RuleEntity> ruleEntities = repo.findByTypeOrderByPriorityAsc(type);
        return ruleEntities.stream()
                .map(entity -> mapper.map(entity, Rule.class))
                .collect(Collectors.toList());
    }
}
