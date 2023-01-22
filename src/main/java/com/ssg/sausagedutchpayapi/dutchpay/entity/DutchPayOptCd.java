package com.ssg.sausagedutchpayapi.dutchpay.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DutchPayOptCd {

    DIVIDE_BY_N("전체 1/N"),

    BY_SECTION("섹션별 정산");

    private final String description;

}
