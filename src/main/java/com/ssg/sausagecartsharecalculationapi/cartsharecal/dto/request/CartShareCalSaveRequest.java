package com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CartShareCalSaveRequest {

    @Schema(description = "공유장바구니 주문 ID")
    private Long cartShareOrdId;

}
