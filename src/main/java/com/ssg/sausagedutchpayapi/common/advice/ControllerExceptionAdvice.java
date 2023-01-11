package com.ssg.sausagedutchpayapi.common.advice;

import static com.ssg.sausagedutchpayapi.common.exception.ErrorCode.INTERNAL_SERVER_EXCEPTION;
import static com.ssg.sausagedutchpayapi.common.exception.ErrorCode.VALIDATION_ENUM_VALUE_EXCEPTION;
import static com.ssg.sausagedutchpayapi.common.exception.ErrorCode.VALIDATION_EXCEPTION;
import static com.ssg.sausagedutchpayapi.common.exception.ErrorCode.VALIDATION_REQUEST_MISSING_EXCEPTION;
import static com.ssg.sausagedutchpayapi.common.exception.ErrorCode.VALIDATION_WRONG_TYPE_EXCEPTION;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ssg.sausagedutchpayapi.common.dto.ErrorResponse;
import com.ssg.sausagedutchpayapi.common.exception.BusinessException;
import com.ssg.sausagedutchpayapi.common.exception.FeignClientException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionAdvice {

    /**
     * 400 BadRequest Spring Validation
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    protected ErrorResponse handleBadRequest(final BindException e) {
        log.error(e.getMessage());
        FieldError fieldError = Objects.requireNonNull(e.getFieldError());
        return ErrorResponse.error(VALIDATION_EXCEPTION,
                String.format("%s (%s)", fieldError.getDefaultMessage(), fieldError.getField()));
    }

    /**
     * 400 BadRequest 잘못된 Enum 값이 입력된 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ErrorResponse handleHttpMessageNotReadableException(
            final HttpMessageNotReadableException e) {
        log.error(e.getMessage());
        return ErrorResponse.error(VALIDATION_ENUM_VALUE_EXCEPTION);
    }

    /**
     * 400 BadRequest RequestParam, RequestPath, RequestPart 등의 필드가 입력되지 않은 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestValueException.class)
    protected ErrorResponse handle(final MissingRequestValueException e) {
        log.error(e.getMessage());
        return ErrorResponse.error(VALIDATION_REQUEST_MISSING_EXCEPTION);
    }

    /**
     * 400 BadRequest 잘못된 타입이 입력된 경우 발생하는 Exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TypeMismatchException.class)
    protected ErrorResponse handleTypeMismatchException(final TypeMismatchException e) {
        log.error(e.getMessage());
        return ErrorResponse.error(VALIDATION_WRONG_TYPE_EXCEPTION,
                String.format("%s (%s)", VALIDATION_WRONG_TYPE_EXCEPTION.getMessage(), e.getValue()));
    }

    /**
     * 400 BadRequest
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            InvalidFormatException.class,
            ServletRequestBindingException.class,
            MethodArgumentTypeMismatchException.class
    })
    protected ErrorResponse handleInvalidFormatException(final Exception e) {
        log.error(e.getMessage());
        return ErrorResponse.error(VALIDATION_EXCEPTION);
    }

    /**
     * Feign Client Exception
     */
    @ExceptionHandler(FeignClientException.class)
    protected ErrorResponse handleFeignClientException(final FeignClientException e) {
        log.error(e.getErrorMessage(), e);
        return ErrorResponse.error(INTERNAL_SERVER_EXCEPTION);
    }

    /**
     * Business Custom Exception
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBaseException(BusinessException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(exception.getStatus())
                .body(ErrorResponse.error(exception.getErrorCode()));
    }

    /**
     * 500 Internal Server
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    protected ErrorResponse handleException(final Exception exception) {
        log.error(exception.getMessage(), exception);
        return ErrorResponse.error(INTERNAL_SERVER_EXCEPTION);
    }
}
