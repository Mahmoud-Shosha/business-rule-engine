package org.gs1eg.business_rule_engine.engine.domain.model;

import lombok.Builder;
import lombok.Data;
import org.gs1eg.business_rule_engine.payment.domain.model.PaymentTransaction;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class PaymentProcessingResult {

    private UUID processingId;
    private PaymentTransaction paymentTransaction;
    private int appliedRulesCount;
    private List<String> appliedRules;
}
