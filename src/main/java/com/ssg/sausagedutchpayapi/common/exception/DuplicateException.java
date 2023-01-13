package com.ssg.sausagedutchpayapi.common.exception;

public class DuplicateException extends BusinessException {

    public DuplicateException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public DuplicateException(String message) {
        super(message, ErrorCode.DUPLICATE_EXCEPTION);
    }
}
