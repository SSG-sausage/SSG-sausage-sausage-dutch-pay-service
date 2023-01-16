package com.ssg.sausagedutchpayapi.dutchpay.dto.request;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class DutchPayDtlUpdateRequest {

    private List<DutchPayDtlUpdateInfo> dutchPayDtlUpdateInfoList;



}
