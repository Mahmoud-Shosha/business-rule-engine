package org.gs1eg.business_rule_engine.shared.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {
    private ErrorEnum errorCode;
    private String error;
}
