package org.gs1eg.business_rule_engine.engine.domain.service;

import org.gs1eg.business_rule_engine.engine.domain.model.PaymentProcessingResult;
import org.gs1eg.business_rule_engine.payment.domain.model.PaymentTransaction;

public interface EngineService {

    PaymentProcessingResult processPaymentTransaction(PaymentTransaction paymentTransaction);

}
