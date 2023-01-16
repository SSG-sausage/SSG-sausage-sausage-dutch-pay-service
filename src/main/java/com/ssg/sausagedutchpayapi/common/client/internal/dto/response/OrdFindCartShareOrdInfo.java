package com.ssg.sausagedutchpayapi.common.client.internal.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class OrdFindCartShareOrdInfo {

    private Long mbrId;

    // 배송비 제외 주문 금액
    private int ordAmt;

}
