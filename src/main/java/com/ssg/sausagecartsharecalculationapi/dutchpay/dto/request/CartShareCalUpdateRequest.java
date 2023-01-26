
package com.ssg.sausagecartsharecalculationapi.dutchpay.dto.request;

import com.ssg.sausagecartsharecalculationapi.dutchpay.entity.CalOptCd;
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
public class CartShareCalUpdateRequest {

    private List<CartShareCalDtlUpdateInfo> cartShareCalDtlList;

    private int calRmd;

    private int CalAmt;

    private CalOptCd calOptCd;

    private int CalDtlAmt;

}
