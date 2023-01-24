package com.ssg.sausagedutchpayapi.dutchpay.dto.response;

import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPayDtl;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
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
public class DutchPayDtlFindInfo {

    @Schema(description = "멤버 id")
    private Long mbrId;

    @Schema(description = "멤버 정산 금액")
    private int dutchPayDtlAmt;

    @Schema(description = "멤버 정산 여부")
    private boolean dutchPayCmplYn;

    // TODO : response 논의 후 추가 필요
    @Schema(description = "멤버 이름")
    private String mbrNm;

    @Schema(description = "본인여부")
    private boolean meYn;

    @Schema(description = "마스터여부")
    private boolean mastrYn;

    @Schema(description = "배송비")
    private int shppAmt;

    @Schema(description = "공동 금액")
    private int commAmt;

    @Schema(description = "개별 금액")
    private int prAmt;

    public static DutchPayDtlFindInfo of(DutchPayDtl dutchPayDtl, String mbrNm, Long mbrId, Long mastrId) {
        return DutchPayDtlFindInfo.builder()
                .mbrId(dutchPayDtl.getMbrId())
                .dutchPayDtlAmt(dutchPayDtl.getDutchPayDtlAmt())
                .dutchPayCmplYn(dutchPayDtl.isDutchPayCmplYn())
                .mbrNm(mbrNm)
                .meYn(mbrId.equals(dutchPayDtl.getMbrId()))
                .mastrYn(dutchPayDtl.getMbrId().equals(mastrId))
                .shppAmt(dutchPayDtl.getShppAmt())
                .commAmt(dutchPayDtl.getCommAmt())
                .prAmt(dutchPayDtl.getPrAmt())
                .build();

    }


}
