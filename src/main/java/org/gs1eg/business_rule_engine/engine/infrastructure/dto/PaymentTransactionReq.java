package org.gs1eg.business_rule_engine.engine.infrastructure.dto;

import lombok.Data;
import org.gs1eg.business_rule_engine.payment.domain.model.PaymentChannel;
import org.gs1eg.business_rule_engine.payment.domain.model.PaymentMethod;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentTransactionReq {
    private UUID id;
    private PaymentChannel channel;
    private PaymentMethod method;
    private String currency;
    private BigDecimal amount;
}
