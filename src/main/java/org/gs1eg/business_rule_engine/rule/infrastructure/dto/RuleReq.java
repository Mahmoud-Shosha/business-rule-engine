package org.gs1eg.business_rule_engine.rule.infrastructure.dto;

import lombok.Data;
import org.gs1eg.business_rule_engine.rule.domain.model.RuleType;

import java.util.UUID;

@Data
public class RuleReq {
    private UUID id;
    private String name;
    private String description;
    private RuleType type;
    private String condition;
    private String action;
    private Integer priority;
}
