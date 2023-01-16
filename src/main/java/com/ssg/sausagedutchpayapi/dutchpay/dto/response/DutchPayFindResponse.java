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

    @Schema(description = "함께쓱정산 id")
    private Long dutchPayId;

    @Schema(description = "공유 장바구니 주문 id")
    private Long cartShareOrdId;

    @Schema(description = "함께쓱정산 나머지")
    private int dutchPayRmd;

    private List<DutchPayDtlFindInfo> dutchPayDtlFindInfoList;

    public static DutchPayFindResponse of(DutchPay dutchPay,
            List<DutchPayDtlFindInfo> dutchPayDtlFindInfoList) {
        return DutchPayFindResponse.builder()
                .dutchPayId(dutchPay.getDutchPayId())
                .cartShareOrdId(dutchPay.getCartShareOrdId())
                .dutchPayRmd(dutchPay.getDutchPayRmd())
                .dutchPayDtlFindInfoList(dutchPayDtlFindInfoList)
                .build();
    }
}
