package com.ssg.sausagecartsharecalculationapi.common.client.internal;

import com.ssg.sausagecartsharecalculationapi.common.client.internal.dto.response.MbrFindListResponse;
import com.ssg.sausagecartsharecalculationapi.common.dto.SuccessResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "SAUSAGE-MEMBER-API")
public interface MbrApiClient {

    @GetMapping(value = "/api/mbr-list", params = "mbrIdList")
    SuccessResponse<MbrFindListResponse> findMbrList(
            @RequestParam List<Long> mbrIdList);

}
