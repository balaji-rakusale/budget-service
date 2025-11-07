package com.optimuminfosystem.pfm.budget_service.exception;

import org.springframework.http.HttpStatus;

public class BudgetServiceException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;
    private final HttpStatus status;

    public BudgetServiceException(String errorCode, String errorMessage, HttpStatus status) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
