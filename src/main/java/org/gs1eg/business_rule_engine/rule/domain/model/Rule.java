package org.gs1eg.business_rule_engine.rule.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class Rule {

    private UUID id;
    private String name;
    private String description;
    private RuleType type;
    private String condition;
    private String action;
    private Integer priority;
    private Instant createdAt;
    private Instant updatedAt;

}
