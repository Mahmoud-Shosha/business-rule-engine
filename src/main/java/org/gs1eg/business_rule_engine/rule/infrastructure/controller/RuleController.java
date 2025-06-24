package org.gs1eg.business_rule_engine.rule.infrastructure.controller;

import lombok.AllArgsConstructor;
import org.gs1eg.business_rule_engine.rule.domain.model.Rule;
import org.gs1eg.business_rule_engine.rule.domain.service.RuleService;
import org.gs1eg.business_rule_engine.rule.infrastructure.dto.RuleReq;
import org.gs1eg.business_rule_engine.rule.infrastructure.dto.RuleRes;
import org.gs1eg.business_rule_engine.shared.exceptions.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/rules")
public class RuleController {

    private final RuleService service;
    private final ModelMapper mapper;


    @GetMapping
    public Page<RuleRes> all(@RequestParam(defaultValue = "0") int pageNumber,
                             @RequestParam(defaultValue = "10") int pageSize) {
        Page<Rule> rulesPage = service.findAll(pageNumber, pageSize);
        return rulesPage.map(rule -> mapper.map(rule, RuleRes.class));
    }

    @GetMapping("/{id}")
    public RuleRes findById(@PathVariable UUID id) {
        Rule rule = service.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return mapper.map(rule, RuleRes.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RuleRes save(@RequestBody RuleReq ruleReq) {
        Rule rule = mapper.map(ruleReq, Rule.class);
        rule = service.save(rule);
        return mapper.map(rule, RuleRes.class);
    }

    @PutMapping("/{id}")
    public RuleRes update(@PathVariable UUID id, @RequestBody RuleReq ruleReq) {
        ruleReq.setId(id);
        Rule rule = mapper.map(ruleReq, Rule.class);
        rule = service.update(rule);
        return mapper.map(rule, RuleRes.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID id) {
        service.deleteById(id);
    }

}
