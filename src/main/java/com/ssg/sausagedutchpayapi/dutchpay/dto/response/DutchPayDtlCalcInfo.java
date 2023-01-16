package com.ssg.sausagedutchpayapi.dutchpay.dto.response;

import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrdFindCartShareOrdInfo;
import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPayDtl;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class DutchPayDtlCalcInfo {

    @Schema(description = "멤버 id")
    private Long mbrId;

    @Schema(description = "멤버 정산 금액")
    private int dutchPayDtlAmt;

    public static DutchPayDtlCalcInfo of(DutchPayDtl dutchPayDtl, int dutchPayDtlAmt) {
        return DutchPayDtlCalcInfo.builder()
                .mbrId(dutchPayDtl.getMbrId())
                .dutchPayDtlAmt(dutchPayDtlAmt)
                .build();
    }

    public static DutchPayDtlCalcInfo of(OrdFindCartShareOrdInfo ordInfo, int amt) {
        return DutchPayDtlCalcInfo.builder()
                .mbrId(ordInfo.getMbrId())
                .dutchPayDtlAmt(ordInfo.getOrdAmt() + amt)
                .build();
    }

}
