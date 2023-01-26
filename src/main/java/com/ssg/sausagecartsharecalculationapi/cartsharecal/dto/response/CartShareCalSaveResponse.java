package com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.response;

import com.ssg.sausagecartsharecalculationapi.cartsharecal.entity.CartShareCal;
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

    @Schema(description = "공유장바구니 정산 ID")
    private Long cartShareCalId;

    public static CartShareCalSaveResponse of(CartShareCal cartShareCal) {
        return CartShareCalSaveResponse.builder()
                .cartShareCalId(cartShareCal.getCartShareCalId())
                .build();
    }

}
