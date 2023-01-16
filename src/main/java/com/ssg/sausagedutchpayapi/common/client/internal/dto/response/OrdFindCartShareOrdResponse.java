package com.ssg.sausagedutchpayapi.common.client.internal.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class OrdFindCartShareOrdResponse {

    private int commAmt;

    List<OrdFindCartShareOrdShppInfo> shppInfoList;

    List<OrdFindCartShareOrdInfo> ordInfoList;


}
