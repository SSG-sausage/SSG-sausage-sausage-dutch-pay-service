
package com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.request;

import com.ssg.sausagecartsharecalculationapi.cartsharecal.entity.CalOptCd;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "정산 나머지")
    private int calRmd;

    @Schema(description = "정산 금액")
    private int calAmt;

    @Schema(description = "정산 옵션 코드")
    private CalOptCd calOptCd;

    @Schema(description = "정산 세부 금액")
    private int calDtlAmt;

}
