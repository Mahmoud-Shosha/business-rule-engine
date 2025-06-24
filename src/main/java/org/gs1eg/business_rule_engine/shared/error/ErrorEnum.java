package org.gs1eg.business_rule_engine.shared.error;

import lombok.Getter;

@Getter
public enum ErrorEnum {
    INTERNAL_SERVER_ERROR("Internal Server error is caught."),
    ENTITY_NOT_FOUND("The requested entity was not found.");

    private final String message;

    ErrorEnum(String message) {
        this.message = message;
    }

}
