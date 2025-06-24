package org.gs1eg.business_rule_engine.engine.domain.service.impl;

import org.gs1eg.business_rule_engine.engine.domain.model.PaymentProcessingResult;
import org.gs1eg.business_rule_engine.payment.domain.model.PaymentProcessor;
import org.gs1eg.business_rule_engine.payment.domain.model.PaymentTransaction;
import org.gs1eg.business_rule_engine.rule.domain.model.Rule;
import org.gs1eg.business_rule_engine.rule.domain.model.RuleType;
import org.gs1eg.business_rule_engine.rule.domain.service.RuleService;
import org.gs1eg.business_rule_engine.rule.domain.service.impl.RuleTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static org.gs1eg.business_rule_engine.payment.domain.model.PaymentChannel.SWIFT;
import static org.gs1eg.business_rule_engine.payment.domain.model.PaymentMethod.CREDIT_CARD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EngineServiceImplTest {

    @Mock
    private RuleService ruleService;

    @Spy
    private ExpressionParser expressionParser = new SpelExpressionParser();

    @InjectMocks
    private EngineServiceImpl engineService;

    private static List<Rule> mockRules;

    @BeforeEach
    void setup() {
        mockRules = RuleTestDataFactory.getRules();
        when(ruleService.findByTypeOrderByPriorityAsc(RuleType.ENRICHMENT))
                .thenReturn(mockRules.stream()
                        .filter(rule -> rule.getType().equals(RuleType.ENRICHMENT))
                        .sorted(Comparator.comparing(Rule::getPriority))
                        .toList());
        when(ruleService.findByTypeOrderByPriorityAsc(RuleType.ROUTING))
                .thenReturn(mockRules.stream()
                        .filter(rule -> rule.getType().equals(RuleType.ROUTING))
                        .sorted(Comparator.comparing(Rule::getPriority))
                        .toList());
    }

    @Test
    void processPaymentTransaction_3EnrichmentRulesMatched() {
        // Given
        PaymentTransaction paymentTransaction = PaymentTransaction.builder()
                .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .channel(SWIFT)
                .method(CREDIT_CARD)
                .currency("USD")
                .amount(BigDecimal.valueOf(99999))
                .build();
        // When
        PaymentProcessingResult result = engineService.processPaymentTransaction(paymentTransaction);
        // Then
        assertNotNull(result);
        assertEquals(2, result.getAppliedRulesCount());
    }

    @Test
    void processPaymentTransaction_2RoutingRulesMatched_1() {
        // Given
        PaymentTransaction paymentTransaction = PaymentTransaction.builder()
                .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .channel(SWIFT)
                .method(CREDIT_CARD)
                .currency("EUR")
                .amount(BigDecimal.valueOf(20000))
                .build();
        // When
        PaymentProcessingResult result = engineService.processPaymentTransaction(paymentTransaction);
        // Then
        assertNotNull(result);
        assertEquals(2, result.getAppliedRulesCount());
        assertEquals(PaymentProcessor.EU_GATEWAY, result.getPaymentTransaction().getProcessor());
    }

    @Test
    void processPaymentTransaction_2RoutingRulesMatched_2() {
        // Given
        PaymentTransaction paymentTransaction = PaymentTransaction.builder()
                .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                .channel(SWIFT)
                .method(CREDIT_CARD)
                .currency("EUR")
                .amount(BigDecimal.valueOf(20))
                .build();
        // When
        PaymentProcessingResult result = engineService.processPaymentTransaction(paymentTransaction);
        // Then
        assertNotNull(result);
        assertEquals(2, result.getAppliedRulesCount());
        assertEquals(PaymentProcessor.FAST_PROCESSOR, result.getPaymentTransaction().getProcessor());
    }

}
