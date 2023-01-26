package com.ssg.sausagecartsharecalculationapi.common.exception;

public class InternalServerException extends BusinessException {

    public InternalServerException(String message) {
        super(message, ErrorCode.INTERNAL_SERVER_EXCEPTION);
    }

    public InternalServerException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
