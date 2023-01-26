package com.ssg.sausagecartsharecalculationapi.common.client.internal;

import com.ssg.sausagecartsharecalculationapi.common.client.internal.dto.response.CartShareOrdFindDetailForCartShareCal;
import com.ssg.sausagecartsharecalculationapi.common.client.internal.dto.response.CartShareOrdFindForCartShareCal;
import com.ssg.sausagecartsharecalculationapi.common.dto.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CartShareOrdApiClient", url = "http://localhost:8080/api")
public interface CartShareOrdApiClient {

    @GetMapping("/cart-share-ord/{cartShareOrdId}")
    SuccessResponse<CartShareOrdFindForCartShareCal> findCartShareOrdForCartShareCal(
            @PathVariable Long cartShareOrdId);

    @GetMapping("/cart-share-ord/{cartShareOrdId}/detail")
    SuccessResponse<CartShareOrdFindDetailForCartShareCal> findCartShareOrdDetailForCartShareCal(
            @PathVariable Long cartShareOrdId);



}
