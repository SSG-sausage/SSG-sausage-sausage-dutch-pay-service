package com.ssg.sausagedutchpayapi.dutchpay.dto.response;

import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrdFindCartShareOrdInfo;
import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPayDtl;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
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

    @Schema(description = "멤버 이름")
    private String mbrNm;

    @Schema(description = "배송비")
    private int shppAmt;

    @Schema(description = "공동 금액")
    private int commAmt;

    @Schema(description = "개별 금액")
    private int prAmt;

    @Schema(description = "마스터여부")
    private boolean mastrYn;

    public static DutchPayDtlCalcInfo of(Long mbrId, String mbrNm, int dutchPayDtlAmt, int commQt, Long mastrMbrId) {
        return DutchPayDtlCalcInfo.builder()
                .mbrId(mbrId)
                .mbrNm(mbrNm)
                .dutchPayDtlAmt(dutchPayDtlAmt + commQt)
                .shppAmt(0)
                .commAmt(commQt)
                .prAmt(dutchPayDtlAmt)
                .mastrYn(mbrId.equals(mastrMbrId))
                .build();
    }

    public void addDutchPayDtlAmt(int dutchPayDtlAmt) {
        this.dutchPayDtlAmt += dutchPayDtlAmt;
    }

    public void addShppAmt(int shppAmt) {
        this.shppAmt += shppAmt;
    }




}
