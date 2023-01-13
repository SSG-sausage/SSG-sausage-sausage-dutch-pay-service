package com.ssg.sausagedutchpayapi.dutchpay.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.ssg.sausagedutchpayapi.common.client.internal.InternalOrderApiClient;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrderFindCartShareResponse;
import com.ssg.sausagedutchpayapi.common.dto.SuccessResponse;
import com.ssg.sausagedutchpayapi.common.exception.DuplicateException;
import com.ssg.sausagedutchpayapi.common.exception.ForbiddenException;
import com.ssg.sausagedutchpayapi.common.success.SuccessCode;
import com.ssg.sausagedutchpayapi.dutchpay.dto.response.DutchPaySaveResponse;
import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPay;
import com.ssg.sausagedutchpayapi.dutchpay.repository.DutchPayDtlRepository;
import com.ssg.sausagedutchpayapi.dutchpay.repository.DutchPayRepository;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DutchPayServiceTest {

    @MockBean
    private InternalOrderApiClient internalOrderApiClient;

    @Autowired
    private DutchPayService dutchPayService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    TransactionStatus transactionStatus;

    @BeforeEach
    void beforeEach() {
        transactionStatus = transactionManager.getTransaction(new DefaultTransactionAttribute());
    }

    @AfterEach
    void afterEach() {
        transactionManager.rollback(transactionStatus);
    }


    @Test
    @DisplayName("함께쓱정산 정상 생성")
    public void testSaveDutchPaySuccess() {

        // given
        Long mbrId = 1L;
        Long masterId = 1L;
        Long cartShareOrdId = 2L;

        given(internalOrderApiClient.findCartShareByCartShareOrdId(any()))
                .willReturn(

                        SuccessResponse.success(SuccessCode.OK_SUCCESS,
                                OrderFindCartShareResponse.builder()
                                        .cartShareId(1L)
                                        .masterId(masterId)
                                        .mbrIdList(Arrays.asList(1L, 2L, 3L))
                                        .build())

                );

        // when
        DutchPaySaveResponse response = dutchPayService.saveDutchPay(mbrId, cartShareOrdId);

        // then
        Assertions.assertEquals(1L, response.getDutchPayId());

    }

    @Test
    @DisplayName("함께쓱정산 생성 권한 없음")
    public void testSaveDutchPayForbidden() {

        // given
        Long mbrId = 1L;
        Long masterId = 2L;
        Long cartShareOrdId = 2L;

        given(internalOrderApiClient.findCartShareByCartShareOrdId(any()))
                .willReturn(

                        SuccessResponse.success(SuccessCode.OK_SUCCESS,
                                OrderFindCartShareResponse.builder()
                                        .cartShareId(1L)
                                        .masterId(masterId)
                                        .mbrIdList(Arrays.asList(1L, 2L, 3L))
                                        .build())

                );

        // when , then
        Assertions.assertThrows(ForbiddenException.class, () -> {
            dutchPayService.saveDutchPay(mbrId, cartShareOrdId);
        });

    }

    @Test
    @DisplayName("함께쓱정산 중복 생성")
    public void testSaveDutchPayDuplicate() {

        // given
        Long mbrId = 1L;
        Long masterId = 1L;
        Long cartShareOrdId = 2L;

        given(internalOrderApiClient.findCartShareByCartShareOrdId(any()))
                .willReturn(

                        SuccessResponse.success(SuccessCode.OK_SUCCESS,
                                OrderFindCartShareResponse.builder()
                                        .cartShareId(1L)
                                        .masterId(masterId)
                                        .mbrIdList(Arrays.asList(1L, 2L, 3L))
                                        .build())

                );

        dutchPayService.saveDutchPay(mbrId, cartShareOrdId);

        // when, then
        Assertions.assertThrows(DuplicateException.class, () -> {
            dutchPayService.saveDutchPay(mbrId, cartShareOrdId);
        });

    }


}
