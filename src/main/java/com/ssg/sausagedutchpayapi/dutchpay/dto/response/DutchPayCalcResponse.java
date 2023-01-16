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

    @Schema(description = "함께쓱정산 id")
    private Long dutchPayId;

    @Schema(description = "함께쓱정산 나머지")
    private int dutchPayRmd;

    private List<DutchPayDtlCalcInfo> dutchPayDtlCalcInfoList;

    public static DutchPayCalcResponse of(Long dutchPayId, int dutchPayRmd,
            List<DutchPayDtlCalcInfo> dutchPayDtlCalcInfoList) {
        return DutchPayCalcResponse.builder()
                .dutchPayId(dutchPayId)
                .dutchPayRmd(dutchPayRmd)
                .dutchPayDtlCalcInfoList(dutchPayDtlCalcInfoList)
                .build();
    }

    public static DutchPayCalcResponse of(Long dutchPayId, int dutchPayRmd, int dutchPayDtlAmt,
            List<DutchPayDtl> dutchPayDtlList
    ) {
        return DutchPayCalcResponse.of(dutchPayId, dutchPayRmd,
                dutchPayDtlList.stream().map(dutchPayDtl -> {
                    return DutchPayDtlCalcInfo.of(dutchPayDtl, dutchPayDtlAmt);
                }).collect(Collectors.toList())
        );
    }

}
