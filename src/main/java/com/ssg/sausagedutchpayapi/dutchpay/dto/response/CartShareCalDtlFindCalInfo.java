package com.ssg.sausagedutchpayapi.dutchpay.dto.response;

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
public class CartShareCalDtlFindCalInfo {

    @Schema(description = "멤버 id")
    private Long mbrId;

    @Schema(description = "멤버 정산 금액")
    private int calDtlAmt;

    @Schema(description = "멤버 이름")
    private String mbrNm;

    @Schema(description = "배송비")
    private int shppCst;

    @Schema(description = "공동 금액")
    private int commAmt;

    @Schema(description = "개별 금액")
    private int perAmt;

    @Schema(description = "마스터여부")
    private boolean mastrYn;

    public static CartShareCalDtlFindCalInfo of(Long mbrId, String mbrNm, int calDtlAmt, int commQt, Long mastrMbrId) {
        return CartShareCalDtlFindCalInfo.builder()
                .mbrId(mbrId)
                .mbrNm(mbrNm)
                .calDtlAmt(calDtlAmt + commQt)
                .shppCst(0)
                .commAmt(commQt)
                .perAmt(calDtlAmt)
                .mastrYn(mbrId.equals(mastrMbrId))
                .build();
    }

    public void addCalDtlAmt(int calDtlAmt) {
        this.calDtlAmt += calDtlAmt;
    }

    public void addShppCst(int shppCst) {
        this.shppCst += shppCst;
    }




}
