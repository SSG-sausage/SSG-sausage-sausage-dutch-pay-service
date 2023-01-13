package com.ssg.sausagedutchpayapi.dutchpay.dto.response;

import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPay;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)

public class DutchPayFindResponse {

    @Schema(description = "더치페이 id")
    private Long dutchPayId;

    @Schema(description = "공유 장바구니 주문 id")
    private Long cartShareOrdId;

    private List<DutchPayDtlInfo> dutchPayDtlInfoList;

    public static DutchPayFindResponse of(DutchPay dutchPay) {
        return DutchPayFindResponse.builder()
                .dutchPayId(dutchPay.getDutchPayId())
                .cartShareOrdId(dutchPay.getCartShareOrdId())
                .dutchPayDtlInfoList(DutchPayDtlInfo.of(dutchPay.getDutchPayDtlList()))
                .build();
    }
}
