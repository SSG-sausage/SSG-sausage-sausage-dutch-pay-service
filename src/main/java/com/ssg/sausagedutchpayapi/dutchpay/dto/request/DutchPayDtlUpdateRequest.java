
package com.ssg.sausagedutchpayapi.dutchpay.dto.request;

import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPayOptCd;
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
public class DutchPayDtlUpdateRequest {

    private List<DutchPayDtlUpdateInfo> dutchPayDtlList;

    private int dutchPayRmd;

    private int dutchPayAmt;

    private DutchPayOptCd dutchPayOptCd;

    private int dutchPayDtlAmt;

}
