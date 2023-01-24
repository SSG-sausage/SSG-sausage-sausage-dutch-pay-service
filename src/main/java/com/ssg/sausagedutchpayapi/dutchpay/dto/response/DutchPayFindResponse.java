package com.ssg.sausagedutchpayapi.dutchpay.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPay;
import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPayOptCd;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.persistence.Column;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class DutchPayFindResponse {

    @Schema(description = "정산 id")
    private Long dutchPayId;

    @Schema(description = "공유 장바구니 주문 id")
    private Long cartShareOrdId;

    @Schema(description = "마스터여부")
    private boolean mastrYn;

    @Schema(description = "정산 시작 여부")
    private boolean dutchPayStYn;

    @Schema(description = "정산 옵션 코드")
    private DutchPayOptCd dutchPayOptCd;

    @Schema(description = "결제 금액")
    private int paymtAmt;

    @Schema(description = "총 정산 금액")
    private int dutchPayAmt;

    @Schema(description = "정산 나머지")
    private int dutchPayRmd;

    private List<DutchPayDtlFindInfo> dutchPayDtlList;

    public static DutchPayFindResponse of(DutchPay dutchPay,
            List<DutchPayDtlFindInfo> dutchPayDtlFindInfoList, Long mbrId) {
        return DutchPayFindResponse.builder()
                .dutchPayId(dutchPay.getDutchPayId())
                .cartShareOrdId(dutchPay.getCartShareOrdId())
                .mastrYn(mbrId.equals(dutchPay.getMastrMbrId()))
                .dutchPayStYn(dutchPay.isDutchPayStYn())
                .dutchPayOptCd(dutchPay.getDutchPayOptCd())
                .paymtAmt(dutchPay.getPaymtAmt())
                .dutchPayRmd(dutchPay.getDutchPayRmd())
                .dutchPayAmt(dutchPay.getDutchPayAmt())
                .dutchPayDtlList(dutchPayDtlFindInfoList)
                .build();
    }
}
