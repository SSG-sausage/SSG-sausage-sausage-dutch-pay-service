package com.ssg.sausagedutchpayapi.dutchpay.service;

import com.ssg.sausagedutchpayapi.common.exception.DuplicateException;
import com.ssg.sausagedutchpayapi.common.exception.ForbiddenException;
import com.ssg.sausagedutchpayapi.dutchpay.dto.response.DutchPaySaveResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class DutchPayServiceTest {

    @Autowired
    private DutchPayService dutchPayService;

    @Test
    @Transactional
    @DisplayName("함께쓱정산 정상 생성")
    public void testSaveDutchPaySuccess() {

        // given
        Long mbrId = 1L;
        Long cartShareOrdId = 2L;

        // when
        DutchPaySaveResponse response = dutchPayService.saveDutchPay(mbrId, cartShareOrdId);

        // then
        Assertions.assertEquals(1L, response.getDutchPayId());

    }

    @Test
    @Transactional
    @DisplayName("함께쓱정산 생성 권한 없음")
    public void testSaveDutchPayForbidden() {

        // given
        Long mbrId = 2L;
        Long cartShareOrdId = 2L;

        // when , then
        Assertions.assertThrows(ForbiddenException.class, () -> {
            dutchPayService.saveDutchPay(mbrId, cartShareOrdId);
        });

    }

    @Test
    @Transactional
    @DisplayName("함께쓱정산 중복 생성")
    public void testSaveDutchPayDuplicate() {

        // given
        Long mbrId = 1L;
        Long cartShareOrdId = 2L;

        dutchPayService.saveDutchPay(mbrId, cartShareOrdId);

        // when, then
        Assertions.assertThrows(DuplicateException.class, () -> {
            dutchPayService.saveDutchPay(mbrId, cartShareOrdId);
        });

    }


}
