package com.ssg.sausagedutchpayapi.common.client.internal;

import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrdFindCartShareOrdResponse;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrdFindCartShareOrdDetailResponse;
import com.ssg.sausagedutchpayapi.common.dto.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "InternalOrderApiClient", url = "http://localhost:8080/api")
public interface InternalOrderApiClient {

    // TODO: order-api internal api url 논의 후 수정예정
    @GetMapping("/cart-share-ord/{cartShareOrdId}")
    ResponseEntity<SuccessResponse<OrdFindCartShareOrdResponse>> findCartShareOrd(
            @PathVariable Long cartShareOrdId);

    @GetMapping("/cart-share-ord/{cartShareOrdId}/detail")
    ResponseEntity<SuccessResponse<OrdFindCartShareOrdDetailResponse>> findCartShareOrdDetail(
            @PathVariable Long cartShareOrdId);

}
