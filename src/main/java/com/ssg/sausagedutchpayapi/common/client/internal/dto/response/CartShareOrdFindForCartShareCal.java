package com.ssg.sausagedutchpayapi.common.client.internal.dto.response;

import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CartShareOrdFindForCartShareCal {

    private Long cartShareId;

    private Long mastrMbrId;

    private Set<Long> mbrIdList;

    private int ttlPaymtAmt;



}
