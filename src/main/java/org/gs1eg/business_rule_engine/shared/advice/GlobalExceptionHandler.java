package org.gs1eg.business_rule_engine.shared.advice;

import org.gs1eg.business_rule_engine.shared.error.ErrorDto;
import org.gs1eg.business_rule_engine.shared.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.gs1eg.business_rule_engine.shared.error.ErrorEnum;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(getErrorDto(ErrorEnum.ENTITY_NOT_FOUND));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGeneralException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getErrorDto(ErrorEnum.INTERNAL_SERVER_ERROR));
    }

    private ErrorDto getErrorDto(ErrorEnum errorEnum) {
        return new ErrorDto(errorEnum, errorEnum.getMessage());
    }
}
