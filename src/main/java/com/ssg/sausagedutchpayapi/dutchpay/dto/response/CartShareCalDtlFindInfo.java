package com.ssg.sausagedutchpayapi.dutchpay.dto.response;

import com.ssg.sausagedutchpayapi.dutchpay.entity.CartShareCalDtl;
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
public class CartShareCalDtlFindInfo {

    @Schema(description = "멤버 id")
    private Long mbrId;

    @Schema(description = "공유장바구니 정산 세부 금액")
    private int cartShareCalDtlAmt;

    @Schema(description = "공유장바구니 정산 완료 여부")
    private boolean cartShareCalDtlCmplYn;

    @Schema(description = "멤버 이름")
    private String mbrNm;

    @Schema(description = "본인여부")
    private boolean meYn;

    @Schema(description = "마스터여부")
    private boolean mastrYn;

    @Schema(description = "배송비")
    private int shppCst;

    @Schema(description = "공동 금액")
    private int commAmt;

    @Schema(description = "개별 금액")
    private int perAmt;

    public static CartShareCalDtlFindInfo of(CartShareCalDtl cartShareCalDtl, String mbrNm, Long mbrId, Long mastrMbrId) {
        return CartShareCalDtlFindInfo.builder()
                .mbrId(cartShareCalDtl.getMbrId())
                .cartShareCalDtlAmt(cartShareCalDtl.getCalDtlAmt())
                .cartShareCalDtlCmplYn(cartShareCalDtl.isCalCmplYn())
                .mbrNm(mbrNm)
                .meYn(mbrId.equals(cartShareCalDtl.getMbrId()))
                .mastrYn(cartShareCalDtl.getMbrId().equals(mastrMbrId))
                .shppCst(cartShareCalDtl.getShppCst())
                .commAmt(cartShareCalDtl.getCommAmt())
                .perAmt(cartShareCalDtl.getPerAmt())
                .build();

    }


}
