package com.ssg.sausagecartsharecalculationapi.common.client.external;

import com.ssg.sausagecartsharecalculationapi.common.client.external.dto.response.ExternalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ExternalApiClient", url = "https://url.com")
public interface ExternalApiClient {

    @GetMapping("/path")
    ExternalResponse test();
}
