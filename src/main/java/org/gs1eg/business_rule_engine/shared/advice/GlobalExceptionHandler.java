package org.gs1eg.business_rule_engine.shared.advice;

import lombok.extern.slf4j.Slf4j;
import org.gs1eg.business_rule_engine.shared.error.ErrorDto;
import org.gs1eg.business_rule_engine.shared.error.ErrorEnum;
import org.gs1eg.business_rule_engine.shared.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorEnum errorEnum = ErrorEnum.ENTITY_NOT_FOUND;
        log.error("{}: {}", errorEnum, errorEnum.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(getErrorDto(errorEnum));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGeneralException(Exception ex) {
        ErrorEnum errorEnum = ErrorEnum.INTERNAL_SERVER_ERROR;
        log.error("{}: {}", errorEnum, errorEnum.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getErrorDto(errorEnum));
    }

    private ErrorDto getErrorDto(ErrorEnum errorEnum) {
        return new ErrorDto(errorEnum, errorEnum.getMessage());
    }
}
