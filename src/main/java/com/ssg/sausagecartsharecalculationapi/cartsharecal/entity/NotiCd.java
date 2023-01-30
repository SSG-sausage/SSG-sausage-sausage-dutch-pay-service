package com.ssg.sausagecartsharecalculationapi.cartsharecal.entity;

import com.ssg.sausagecartsharecalculationapi.common.exception.ErrorCode;
import com.ssg.sausagecartsharecalculationapi.common.exception.InternalServerException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotiCd {

    CART_SHARE_CAL("쓱총무");

    private final String description;

    public static NotiCd of(String notiCd) {
        if (notiCd.equals("CART_SHARE_CAL")) {
            return CART_SHARE_CAL;
        }
        throw new InternalServerException("예상치 못한 서버 에러가 발생하였습니다.", ErrorCode.INTERNAL_SERVER_EXCEPTION);
    }
}
