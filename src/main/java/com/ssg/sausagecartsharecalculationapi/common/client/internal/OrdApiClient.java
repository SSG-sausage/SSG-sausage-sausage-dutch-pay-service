package com.ssg.sausagecartsharecalculationapi.common.client.internal;

import com.ssg.sausagecartsharecalculationapi.common.client.internal.dto.response.CartShareOrdFindDetailForCartShareCal;
import com.ssg.sausagecartsharecalculationapi.common.client.internal.dto.response.CartShareOrdFindForCartShareCal;
import com.ssg.sausagecartsharecalculationapi.common.dto.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SAUSAGE-ORDER-API")
public interface OrdApiClient {

    @GetMapping("/api/cart-share-ord/{cartShareOrdId}/cart-share-cal")
    SuccessResponse<CartShareOrdFindForCartShareCal> findCartShareOrdForCartShareCal(
            @PathVariable Long cartShareOrdId);

    @GetMapping("/api/cart-share-ord/{cartShareOrdId}/cart-share-cal/detail")
    SuccessResponse<CartShareOrdFindDetailForCartShareCal> findCartShareOrdDetailForCartShareCal(
            @PathVariable Long cartShareOrdId);



}
