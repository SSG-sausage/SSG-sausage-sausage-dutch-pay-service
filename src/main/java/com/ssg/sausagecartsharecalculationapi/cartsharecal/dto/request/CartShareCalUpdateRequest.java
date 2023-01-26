
package com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.request;

import com.ssg.sausagecartsharecalculationapi.cartsharecal.entity.CalOptCd;
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

    private int calAmt;

    private CalOptCd calOptCd;

    private int calDtlAmt;

}
