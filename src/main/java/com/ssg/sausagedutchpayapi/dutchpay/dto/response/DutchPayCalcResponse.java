package com.ssg.sausagedutchpayapi.dutchpay.dto.response;

import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPayDtl;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class DutchPayCalcResponse {

    @Schema(description = "정산 id")
    private Long dutchPayId;

    @Schema(description = "정산 나머지")
    private int dutchPayRmd;

    @Schema(description = "총 정산 금액")
    private int dutchPayAmt;

    private List<DutchPayDtlCalcInfo> dutchPayDtlList;

    public static DutchPayCalcResponse of(Long dutchPayId, int dutchPayRmd, int dutchPayAmt,
            List<DutchPayDtlCalcInfo> dutchPayDtlCalcInfoList) {
        return DutchPayCalcResponse.builder()
                .dutchPayId(dutchPayId)
                .dutchPayRmd(dutchPayRmd)
                .dutchPayAmt(dutchPayAmt)
                .dutchPayDtlList(dutchPayDtlCalcInfoList)
                .build();
    }



}
