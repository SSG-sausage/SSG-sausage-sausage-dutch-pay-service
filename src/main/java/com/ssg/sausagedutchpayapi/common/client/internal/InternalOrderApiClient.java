package com.ssg.sausagedutchpayapi.common.client.internal;

import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrderFindCartShareResponse;
import com.ssg.sausagedutchpayapi.common.dto.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "InternalOrderApiClient", url = "http://localhost:8084/api")
public interface InternalOrderApiClient {

    // TODO: order-api internal api url 논의 후 수정예정
    @GetMapping("/cart-share/info/cart-share-order/{cartShareOrdId}")
    ResponseEntity<SuccessResponse<OrderFindCartShareResponse>> findCartShareByCartShareOrdId(
            @PathVariable Long cartShareOrdId);
}
