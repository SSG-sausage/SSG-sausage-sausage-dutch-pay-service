package com.ssg.sausagedutchpayapi.common.client.internal;

import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.MbrFindInfo;
import com.ssg.sausagedutchpayapi.common.dto.SuccessResponse;
import java.util.HashMap;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "InternalMemberApiClient", url = "http://localhost:8080/api")

public interface InternalMemberApiClient {

    @GetMapping("/mbr-list")
    ResponseEntity<SuccessResponse<HashMap<Long, MbrFindInfo>>> findMbrList(
            @RequestParam List<Long> mbrIdList);

}
