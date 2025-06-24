package org.gs1eg.business_rule_engine.rule.infrastructure.controller;

import lombok.AllArgsConstructor;
import org.gs1eg.business_rule_engine.rule.domain.model.Rule;
import org.gs1eg.business_rule_engine.rule.domain.service.RuleService;
import org.gs1eg.business_rule_engine.shared.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/rules")
public class RuleController {

    private final RuleService service;


    @GetMapping
    public Page<Rule> all(@RequestParam(defaultValue = "0") int pageNumber,
                          @RequestParam(defaultValue = "10") int pageSize) {
        return service.findAll(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public Rule findById(@PathVariable UUID id) {
        return service.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rule save(@RequestBody Rule rule) {
        return service.save(rule);
    }

    @PutMapping("/{id}")
    public Rule update(@PathVariable UUID id, @RequestBody Rule rule) {
        rule.setId(id);
        return service.update(rule);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID id) {
        service.deleteById(id);
    }

}
