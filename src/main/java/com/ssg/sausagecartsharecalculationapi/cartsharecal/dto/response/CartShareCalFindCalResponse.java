package com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.response;

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
@Builder(access = AccessLevel.PRIVATE)
public class CartShareCalFindCalResponse {

    @Schema(description = "정산 ID")
    private Long cartShareCalId;

    @Schema(description = "정산 나머지")
    private int calRmd;

    @Schema(description = "총 정산 금액")
    private int calAmt;

    private List<CartShareCalDtlFindCalInfo> cartShareCalDtlList;

    public static CartShareCalFindCalResponse of(Long dutchPayId, int calRmd, int calAmt,
            List<CartShareCalDtlFindCalInfo> cartShareCalDtlList) {
        return CartShareCalFindCalResponse.builder()
                .cartShareCalId(dutchPayId)
                .calRmd(calRmd)
                .calAmt(calAmt)
                .cartShareCalDtlList(cartShareCalDtlList)
                .build();
    }

}
