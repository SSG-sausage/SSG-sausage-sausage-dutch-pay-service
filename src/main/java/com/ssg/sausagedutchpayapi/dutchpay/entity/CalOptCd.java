package com.ssg.sausagedutchpayapi.dutchpay.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CalOptCd {

    SECTION("섹션별 계산"),

    SPLIT("1/N 계산"),

    INPUT("직접 입력");

    private final String description;

}
