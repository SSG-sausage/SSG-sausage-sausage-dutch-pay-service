package com.ssg.sausagedutchpayapi.dutchpay.service;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.ssg.sausagedutchpayapi.common.client.internal.InternalOrderApiClient;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrdFindCartShareOrdInfo;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrdFindCartShareOrdResponse;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrdFindCartShareOrdShppInfo;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrdFindTotalPriceResponse;
import com.ssg.sausagedutchpayapi.common.dto.SuccessResponse;
import com.ssg.sausagedutchpayapi.common.success.SuccessCode;
import com.ssg.sausagedutchpayapi.dutchpay.dto.response.DutchPayCalcResponse;
import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPay;
import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPayDtl;
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
    @DisplayName("정산 결제금액 전체 1/N 계산 : case1")
    public void testCalcDutchPay() {
        // given
        Long dutchPayId = 1L;
        Long cartShareOrdId = 100L;
        int mbrNum = 4;
        int opt = 1;

        given(internalOrderApiClient.findCartShareOrdTotalPrice(cartShareOrdId)).willReturn(
                SuccessResponse.success(SuccessCode.OK_SUCCESS,
                        OrdFindTotalPriceResponse.builder().totalPrice(2500).build()));

        given(dutchPayRepository.findById(dutchPayId)).willReturn(
                Optional.ofNullable(createDutchPay(mbrNum)));

        // when
        DutchPayCalcResponse response = dutchPayService.calcDutchPay(dutchPayId, opt);

        // then
        assertAll(() -> assertEquals(0, response.getDutchPayRmd()),
                () -> assertEquals(625,
                        response.getDutchPayDtlCalcInfoList().get(0).getDutchPayDtlAmt()),
                () -> assertEquals(625,
                        response.getDutchPayDtlCalcInfoList().get(1).getDutchPayDtlAmt()),
                () -> assertEquals(625,
                        response.getDutchPayDtlCalcInfoList().get(2).getDutchPayDtlAmt()),
                () -> assertEquals(625,
                        response.getDutchPayDtlCalcInfoList().get(3).getDutchPayDtlAmt())
        );

    }

    @Test
    @DisplayName("참여자별 정산 : case1")
    public void testCalcPersonal() {
        // given
        Long dutchPayId = 1L;
        Long cartShareOrdId = 100L;
        int mbrNum = 4;
        int opt = 2;

        OrdFindCartShareOrdShppInfo shppInfo1 = OrdFindCartShareOrdShppInfo.builder()
                .shppCst(4000).mbrIdList(Arrays.asList(1L, 2L, 3L)).build();
        OrdFindCartShareOrdShppInfo shppInfo2 = OrdFindCartShareOrdShppInfo.builder()
                .shppCst(3000).mbrIdList(Arrays.asList(1L, 2L, 3L, 4L)).build();

        OrdFindCartShareOrdInfo orderInfo1 = OrdFindCartShareOrdInfo.builder().mbrId(1L)
                .ordAmt(10000).build();

        OrdFindCartShareOrdInfo orderInfo2 = OrdFindCartShareOrdInfo.builder().mbrId(2L)
                .ordAmt(3000).build();

        OrdFindCartShareOrdInfo orderInfo3 = OrdFindCartShareOrdInfo.builder().mbrId(3L)
                .ordAmt(4000).build();

        OrdFindCartShareOrdInfo orderInfo4 = OrdFindCartShareOrdInfo.builder().mbrId(4L)
                .ordAmt(0).build();

        OrdFindCartShareOrdResponse ordResponse = OrdFindCartShareOrdResponse.builder()
                .commAmt(5230).shppInfoList(Arrays.asList(shppInfo1, shppInfo2))
                .ordInfoList(Arrays.asList(orderInfo1, orderInfo2, orderInfo3, orderInfo4)).build();

        given(internalOrderApiClient.findCartShareOrd(cartShareOrdId)).willReturn(
                SuccessResponse.success(SuccessCode.OK_SUCCESS, ordResponse));

        given(dutchPayRepository.findById(dutchPayId)).willReturn(
                Optional.ofNullable(createDutchPay(mbrNum)));

        // when
        DutchPayCalcResponse response = dutchPayService.calcDutchPay(dutchPayId, opt);

        // then
        assertAll(() -> assertEquals(3, response.getDutchPayRmd()),
                () -> assertEquals(13390,
                        response.getDutchPayDtlCalcInfoList().get(0).getDutchPayDtlAmt()),
                () -> assertEquals(6390,
                        response.getDutchPayDtlCalcInfoList().get(1).getDutchPayDtlAmt()),
                () -> assertEquals(7390,
                        response.getDutchPayDtlCalcInfoList().get(2).getDutchPayDtlAmt()),
                () -> assertEquals(2057,
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
