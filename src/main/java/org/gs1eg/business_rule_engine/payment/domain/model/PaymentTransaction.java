package org.gs1eg.business_rule_engine.payment.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class PaymentTransaction {

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
