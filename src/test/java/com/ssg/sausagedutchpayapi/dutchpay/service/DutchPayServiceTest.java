package com.ssg.sausagedutchpayapi.dutchpay.service;

import com.ssg.sausagedutchpayapi.common.exception.ConflictException;
import com.ssg.sausagedutchpayapi.common.exception.ForbiddenException;
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
        Assertions.assertThrows(ConflictException.class, () -> {
            dutchPayService.saveDutchPay(mbrId, cartShareOrdId);
        });

    }


}
