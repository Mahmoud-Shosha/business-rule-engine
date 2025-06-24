package org.gs1eg.business_rule_engine.engine.infrastructure.dto;

import lombok.Data;
import org.gs1eg.business_rule_engine.payment.domain.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class PaymentTransactionRes {

    private UUID processingId;
    private PaymentTransaction paymentTransaction;
    private int appliedRulesCount;
    private List<String> appliedRules;

    @Data
    public static class PaymentTransaction {
        private UUID id;
        private PaymentChannel channel;
        private PaymentMethod method;
        private PaymentDirection direction;
        private PaymentRisk risk;
        private PaymentProcessor processor;
        private Set<String> tags;
        private String currency;
        private BigDecimal amount;
    }

}
