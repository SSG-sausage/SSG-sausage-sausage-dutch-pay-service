package com.ssg.sausagedutchpayapi.dutchpay.dto.response;

import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPay;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class DutchPaySaveResponse {

    @Schema(description = "더치페이 id")
    private Long dutchPayId;

    public static DutchPaySaveResponse of(DutchPay dutchPay) {
        return DutchPaySaveResponse.builder().dutchPayId(dutchPay.getDutchPayId()).build();
    }
}
