package com.ssg.sausagedutchpayapi.dutchpay.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CartShareCalDtlUpdateInfo {

    @Schema(description = "멤버 id")
    private Long mbrId;

    @Schema(description = "정산 세부 금액")
    private int calDtlAmt;

    @Schema(description = "배송비")
    private int shppAmt;

    @Schema(description = "공동 금액")
    private int commAmt;

    @Schema(description = "개별 금액")
    private int prAmt;

}
