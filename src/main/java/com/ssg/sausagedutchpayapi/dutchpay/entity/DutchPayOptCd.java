package com.ssg.sausagedutchpayapi.dutchpay.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DutchPayOptCd {

    DIVIDE_ALL("전체 금액 1/N 정산"),

    PERSONAL("섹션별 정산");

    private final String description;

}
