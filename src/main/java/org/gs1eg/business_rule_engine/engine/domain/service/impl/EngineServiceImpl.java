package org.gs1eg.business_rule_engine.engine.domain.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gs1eg.business_rule_engine.engine.domain.model.PaymentProcessingResult;
import org.gs1eg.business_rule_engine.engine.domain.service.EngineService;
import org.gs1eg.business_rule_engine.payment.domain.model.PaymentTransaction;
import org.gs1eg.business_rule_engine.payment.domain.validator.PaymentValidator;
import org.gs1eg.business_rule_engine.rule.domain.model.Rule;
import org.gs1eg.business_rule_engine.rule.domain.model.RuleType;
import org.gs1eg.business_rule_engine.rule.domain.service.RuleService;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
class EngineServiceImpl implements EngineService {

    private final RuleService ruleService;
    private final ExpressionParser expressionParser;
    private final PaymentValidator paymentValidator;

    @Override
    public PaymentProcessingResult processPaymentTransaction(PaymentTransaction paymentTransaction) {
        log.info("Starting Processing for Payment Transaction: {}", paymentTransaction.getId());
        paymentTransaction.setTags(new HashSet<>());
        PaymentProcessingResult result = PaymentProcessingResult.builder()
                .processingId(UUID.randomUUID())
                .appliedRulesCount(0)
                .appliedRules(new LinkedList<>())
                .paymentTransaction(paymentTransaction)
                .build();
        log.info("Handling Enrichment Rules for Payment Transaction: {}", paymentTransaction.getId());
        handleEnrichmentRules(result);
        log.info("Handling Routing Rules for Payment Transaction: {}", paymentTransaction.getId());
        handleRoutingRules(result);
        return result;
    }

    private void handleEnrichmentRules(PaymentProcessingResult result) {
        List<Rule> enrichmentRules = ruleService.findByTypeOrderByPriorityAsc(RuleType.ENRICHMENT);
        PaymentTransaction transaction = result.getPaymentTransaction();
        enrichmentRules.stream()
                .filter(rule -> evaluateCondition(rule.getCondition(), transaction))
                .forEach(rule -> {
                    applyAction(rule.getAction(), transaction);
                    result.setAppliedRulesCount(result.getAppliedRulesCount() + 1);
                    result.getAppliedRules().add(rule.getName());
                });
    }

    private void handleRoutingRules(PaymentProcessingResult result) {
        List<Rule> enrichmentRules = ruleService.findByTypeOrderByPriorityAsc(RuleType.ROUTING);
        PaymentTransaction transaction = result.getPaymentTransaction();
        enrichmentRules.stream()
                .filter(rule -> evaluateCondition(rule.getCondition(), transaction))
                .findFirst()
                .ifPresent(rule -> {
                    applyAction(rule.getAction(), transaction);
                    result.setAppliedRulesCount(result.getAppliedRulesCount() + 1);
                    result.getAppliedRules().add(rule.getName());
                });
    }

    private boolean evaluateCondition(String condition, PaymentTransaction transaction) {
        StandardEvaluationContext context = new StandardEvaluationContext(transaction);
        return Boolean.TRUE.equals(expressionParser.parseExpression(condition).getValue(context, Boolean.class));
    }

    private void applyAction(String action, PaymentTransaction transaction) {
        StandardEvaluationContext context = new StandardEvaluationContext(transaction);
        expressionParser.parseExpression(action).getValue(context);
    }


}
