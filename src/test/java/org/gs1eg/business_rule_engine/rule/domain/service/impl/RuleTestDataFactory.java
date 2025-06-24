package org.gs1eg.business_rule_engine.rule.domain.service.impl;

import org.gs1eg.business_rule_engine.rule.domain.model.Rule;
import org.gs1eg.business_rule_engine.rule.domain.model.RuleType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class RuleTestDataFactory {

    public static List<Rule> getRules() {
        Rule rule1 = new Rule(
                UUID.fromString("9ddfa054-b1ee-4fe3-a459-b21fc548f8c8"),
                "Credit Risky Transaction",
                "Tag credit transactions as credit risky.",
                RuleType.ENRICHMENT,
                "method.name == \"CREDIT_CARD\"",
                "tags.add(\"Risky\")",
                9,
                Instant.parse("2024-06-01T10:15:30Z"),
                Instant.parse("2024-06-10T11:15:30Z")
        );

        Rule rule2 = new Rule(
                UUID.fromString("82c91bf8-7b02-4963-a8f0-9b20f45a5597"),
                "International Transfer",
                "Tag international transfers.",
                RuleType.ENRICHMENT,
                "channel.name == \"SWIFT\" && currency == \"USD\"",
                "tags.add(\"International transfer\")",
                4,
                Instant.parse("2024-06-01T10:15:30Z"),
                Instant.parse("2024-06-10T11:15:30Z")
        );

        Rule rule3 = new Rule(
                UUID.fromString("b2c3d4e5-f6a7-8b9c-0d1e-f2a3b4c5d6e7"),
                "Fast Transaction",
                "Route fast transaction to fast processor",
                RuleType.ROUTING,
                "method.name == \"CREDIT_CARD\" && amount < 100",
                "processor = \"FAST_PROCESSOR\"",
                3,
                Instant.parse("2024-06-02T08:00:00Z"),
                Instant.parse("2024-06-12T08:00:00Z")
        );

        Rule rule4 = new Rule(
                UUID.fromString("3bb3b7b8-5c12-4f71-8de7-5171ca3e9cdd"),
                "Euro Currency Routing",
                "Route Euro currency to Euro gateway.",
                RuleType.ROUTING,
                "currency == \"EUR\"",
                "processor = \"EU_GATEWAY\"",
                9,
                Instant.parse("2024-06-02T08:00:00Z"),
                Instant.parse("2024-06-12T08:00:00Z")
        );

        return List.of(rule1, rule2, rule3, rule4);
    }

    public static Rule getNewRule() {
        return Rule.builder()
                .name("High Transaction Amount")
                .description("Set high risk if the transaction amont is greater than 10,000.")
                .type(RuleType.ENRICHMENT)
                .condition("amount > 10000")
                .action("risk = \"HIGH\"")
                .build();
    }

}
