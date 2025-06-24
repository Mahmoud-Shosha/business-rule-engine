package org.gs1eg.business_rule_engine.rule.domain.model;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
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
