package org.gs1eg.business_rule_engine.engine.infrastructure.controller;

import lombok.AllArgsConstructor;
import org.gs1eg.business_rule_engine.engine.domain.model.PaymentProcessingResult;
import org.gs1eg.business_rule_engine.engine.domain.service.EngineService;
import org.gs1eg.business_rule_engine.engine.infrastructure.dto.PaymentTransactionReq;
import org.gs1eg.business_rule_engine.engine.infrastructure.dto.PaymentTransactionRes;
import org.gs1eg.business_rule_engine.payment.domain.model.PaymentTransaction;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/engine")
public class EngineController {

    private final EngineService service;
    private final ModelMapper mapper;


    @PostMapping("payment-transaction/processing")
    public PaymentTransactionRes processPaymentTransaction(@RequestBody PaymentTransactionReq req) {
        PaymentTransaction paymentTransaction = mapper.map(req, PaymentTransaction.class);
        PaymentProcessingResult paymentProcessingResult = service.processPaymentTransaction(paymentTransaction);
        return mapper.map(paymentProcessingResult, PaymentTransactionRes.class);
    }


}
