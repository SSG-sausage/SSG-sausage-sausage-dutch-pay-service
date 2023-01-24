package com.ssg.sausagedutchpayapi.dutchpay.service;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.ssg.sausagedutchpayapi.common.client.internal.InternalOrderApiClient;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrdFindCartShareOrdInfo;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrdFindCartShareOrdDetailResponse;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrdFindCartShareOrdShppInfo;
import com.ssg.sausagedutchpayapi.common.dto.SuccessResponse;
import com.ssg.sausagedutchpayapi.common.success.SuccessCode;
import com.ssg.sausagedutchpayapi.dutchpay.dto.response.DutchPayCalcResponse;
import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPay;
import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPayDtl;
import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPayOptCd;
import com.ssg.sausagedutchpayapi.dutchpay.repository.DutchPayRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DutchPayServiceUnitTest {

    @Mock
    private DutchPayRepository dutchPayRepository;
    @Mock
    private InternalOrderApiClient internalOrderApiClient;

    @InjectMocks
    private DutchPayService dutchPayService;

    @Test
    @DisplayName("1-1. 전체 1/N 계산 + 배송비 유료")
    public void testCalcDutchPay() {
        // given
        Long dutchPayId = 1L;
        Long cartShareOrdId = 100L;
        int mbrNum = 4;
        DutchPayOptCd dutchPayOptCd = DutchPayOptCd.DIVIDE_BY_N;

        given(internalOrderApiClient.findCartShareOrdTotalPrice(cartShareOrdId)).willReturn(
                SuccessResponse.success(SuccessCode.OK_SUCCESS,
                        OrdFindTotalPriceResponse.builder().totalPrice(40250).build()));

        given(dutchPayRepository.findById(dutchPayId)).willReturn(
                Optional.ofNullable(createDutchPay(mbrNum)));

        // when
        DutchPayCalcResponse response = dutchPayService.calcDutchPay(dutchPayId, dutchPayOptCd);

        // then
        assertAll(() -> assertEquals(2, response.getDutchPayRmd()),
                () -> assertEquals(10062,
                        response.getDutchPayDtlCalcInfoList().get(0).getDutchPayDtlAmt()),
                () -> assertEquals(10062,
                        response.getDutchPayDtlCalcInfoList().get(1).getDutchPayDtlAmt()),
                () -> assertEquals(10062,
                        response.getDutchPayDtlCalcInfoList().get(2).getDutchPayDtlAmt()),
                () -> assertEquals(10062,
                        response.getDutchPayDtlCalcInfoList().get(3).getDutchPayDtlAmt())
        );

    }

    @Test
    @DisplayName("2-1. 섹션별 계산 + 공동 상품 + 배송비 유료")
    public void testCalcPersonal() {
        // given
        Long dutchPayId = 1L;
        Long cartShareOrdId = 100L;
        int mbrNum = 4;
        DutchPayOptCd dutchPayOptCd = DutchPayOptCd.BY_SECTION;


        OrdFindCartShareOrdShppInfo shppInfo1 = OrdFindCartShareOrdShppInfo.builder()
                .shppCst(3000).mbrIdList(Arrays.asList(1L, 2L)).build();
        OrdFindCartShareOrdShppInfo shppInfo2 = OrdFindCartShareOrdShppInfo.builder()
                .shppCst(4000).mbrIdList(Arrays.asList(1L, 2L, 3L, 4L)).build();

        OrdFindCartShareOrdInfo orderInfo1 = OrdFindCartShareOrdInfo.builder().mbrId(1L)
                .ordAmt(9980).build();

        OrdFindCartShareOrdInfo orderInfo2 = OrdFindCartShareOrdInfo.builder().mbrId(2L)
                .ordAmt(18890).build();

        OrdFindCartShareOrdInfo orderInfo3 = OrdFindCartShareOrdInfo.builder().mbrId(3L)
                .ordAmt(4380).build();

        OrdFindCartShareOrdInfo orderInfo4 = OrdFindCartShareOrdInfo.builder().mbrId(4L)
                .ordAmt(0).build();

        OrdFindCartShareOrdDetailResponse ordResponse = OrdFindCartShareOrdDetailResponse.builder()
                .commAmt(25870).shppInfoList(Arrays.asList(shppInfo1, shppInfo2))
                .ordInfoList(Arrays.asList(orderInfo1, orderInfo2, orderInfo3, orderInfo4)).build();

        given(internalOrderApiClient.findCartShareOrd(cartShareOrdId)).willReturn(
                SuccessResponse.success(SuccessCode.OK_SUCCESS, ordResponse));

        given(dutchPayRepository.findById(dutchPayId)).willReturn(
                Optional.ofNullable(createDutchPay(mbrNum)));

        // when
        DutchPayCalcResponse response = dutchPayService.calcDutchPay(dutchPayId, dutchPayOptCd);

        // then
        assertAll(() -> assertEquals(2, response.getDutchPayRmd()),
                () -> assertEquals(18947,
                        response.getDutchPayDtlCalcInfoList().get(0).getDutchPayDtlAmt()),
                () -> assertEquals(27857,
                        response.getDutchPayDtlCalcInfoList().get(1).getDutchPayDtlAmt()),
                () -> assertEquals(11847,
                        response.getDutchPayDtlCalcInfoList().get(2).getDutchPayDtlAmt()),
                () -> assertEquals(7467,
                        response.getDutchPayDtlCalcInfoList().get(3).getDutchPayDtlAmt()));
    }

    private DutchPay createDutchPay(int mbrNum) {
        DutchPay dutchPay = DutchPay.builder().dutchPayId(1L).cartShareOrdId(100L)
                .dutchPayRmd(0).build();

        Long mbrId = 1L;
        List<DutchPayDtl> dutchPayDtlList = new ArrayList<>();
        for (int i = 0; i < mbrNum; i++) {
            dutchPayDtlList.add(DutchPayDtl.newInstance(dutchPay, mbrId++));
        }

        return DutchPay.builder()
                .dutchPayId(1L)
                .cartShareOrdId(100L)
                .dutchPayDtlList(dutchPayDtlList)
                .build();
    }
}
