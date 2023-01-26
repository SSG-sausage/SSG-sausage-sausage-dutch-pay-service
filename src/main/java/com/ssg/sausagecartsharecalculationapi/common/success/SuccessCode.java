package com.ssg.sausagecartsharecalculationapi.common.success;

import static com.ssg.sausagecartsharecalculationapi.common.success.SuccessStatusCode.CREATED;
import static com.ssg.sausagecartsharecalculationapi.common.success.SuccessStatusCode.OK;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    /**
     * 200 OK
     */
    OK_SUCCESS(OK, "성공입니다."),

    /**
     * 201 CREATED
     */
    CREATED_SUCCESS(CREATED, "생성 성공입니다."),

    /**
     * 202 ACCEPTED
     */

    /**
     * 204 NO_CONTENT
     */
    ;

    private final SuccessStatusCode statusCode;
    private final String message;

    public int getStatus() {
        return statusCode.getStatus();
    }
}
