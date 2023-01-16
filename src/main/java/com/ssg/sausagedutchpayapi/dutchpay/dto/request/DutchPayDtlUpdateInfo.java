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
@Builder(access = AccessLevel.PRIVATE)
public class DutchPayDtlUpdateInfo {

    @Schema(description = "멤버 id", requiredMode = RequiredMode.REQUIRED)
    private Long mbrId;

    @Schema(description = "멤버 정산 금액", requiredMode = RequiredMode.REQUIRED)
    private int dutchPayDtlAmt;

}
