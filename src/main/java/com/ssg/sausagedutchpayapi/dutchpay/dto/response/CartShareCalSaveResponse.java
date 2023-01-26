package com.ssg.sausagedutchpayapi.dutchpay.dto.response;

import com.ssg.sausagedutchpayapi.dutchpay.entity.CartShareCal;
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
public class CartShareCalSaveResponse {

    @Schema(description = "쓱총무 id")
    private Long cartShareCalId;

    public static CartShareCalSaveResponse of(CartShareCal cartShareCal) {
        return CartShareCalSaveResponse.builder()
                .cartShareCalId(cartShareCal.getCartShareCalId())
                .build();
    }

}
