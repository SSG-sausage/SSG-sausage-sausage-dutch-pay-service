package com.ssg.sausagedutchpayapi.dutchpay.dto.response;

import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPay;
import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPayDtl;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class DutchPayDtlInfo {

    @Schema(description = "멤버 id")
    private Long mbrId;

    @Schema(description = "멤버 정산 금액")
    private int dutchPayDtlAmt;

    @Schema(description = "멤버 정산 여부")
    private boolean dutchPayCmplYn;

    // TODO : response 논의 후 추가 필요
    @Schema(description = "멤버 이름")
    private String mbrNM;

    public static DutchPayDtlInfo of(DutchPayDtl dutchPayDtl) {
        return DutchPayDtlInfo.builder()
                .mbrId(dutchPayDtl.getMbrId())
                .dutchPayDtlAmt(dutchPayDtl.getDutchPayDtlAmt())
                .dutchPayCmplYn(dutchPayDtl.isDutchPayCmplYn())
                .build();
    }

    public static List<DutchPayDtlInfo> of(List<DutchPayDtl> dutchPayDtlList) {
        return dutchPayDtlList.stream().map(DutchPayDtlInfo::of).collect(Collectors.toList());
    }

}
