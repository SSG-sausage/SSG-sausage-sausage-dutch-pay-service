package com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.response;

import com.ssg.sausagecartsharecalculationapi.cartsharecal.entity.CartShareCal;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class CartShareCalFindListResponse {

    @Schema(description = "공유장바구니 정산 ID")
    private Long cartShareCalId;

    @Schema(description = "공유장바구니 주문 ID")
    private Long cartShareOrdId;

    @Schema(description = "정산 금액")
    private int calAmt;

    @Schema(description = "결제 금액")
    private int ttlPaymtAmt;

    @Schema(description = "정산 시작 일시")
    private LocalDateTime cartShareCalStDts;

    @Schema(description = "주무번호")
    private String cartShareOrdNo;

    @Schema(description = "공유장바구니 이름")
    private String cartShareNm;

    @Schema(description = "공유장바구니 멤버 수")
    private int mbrNum;

    @Schema(description = "전체 정산 완료 여부")
    private boolean cmplYn;

    public static CartShareCalFindListResponse of(CartShareCal cartShareCal) {
        return CartShareCalFindListResponse.builder()
                .cartShareCalId(cartShareCal.getCartShareCalId())
                .cartShareOrdId(cartShareCal.getCartShareOrdId())
                .calAmt(cartShareCal.getCalAmt())
                .ttlPaymtAmt(cartShareCal.getTtlPaymtAmt())
                .cartShareOrdNo(cartShareCal.getCartShareOrdNo())
                .cartShareNm(cartShareCal.getCartShareNm())
                .cartShareCalStDts(cartShareCal.getCartShareCalStDts())
                .mbrNum(cartShareCal.getCartShareCalDtlList().size())
                .cmplYn(cartShareCal.getCartShareCalDtlList().stream()
                        .map(cartShareCalDtl -> cartShareCalDtl.isCalCmplYn())
                        .reduce(true, (a, b) -> a && b))
                .build();
    }


}
