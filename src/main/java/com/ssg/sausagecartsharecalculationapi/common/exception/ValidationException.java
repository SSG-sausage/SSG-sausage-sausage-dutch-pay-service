package com.ssg.sausagecartsharecalculationapi.common.exception;

public class ValidationException extends BusinessException {

    public ValidationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ValidationException(String message) {
        super(message, ErrorCode.VALIDATION_EXCEPTION);
    }
}
