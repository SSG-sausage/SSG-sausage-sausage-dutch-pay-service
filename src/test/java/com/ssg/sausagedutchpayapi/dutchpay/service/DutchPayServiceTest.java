package com.ssg.sausagedutchpayapi.dutchpay.service;

import com.ssg.sausagedutchpayapi.common.exception.ConflictException;
import com.ssg.sausagedutchpayapi.common.exception.ForbiddenException;
import com.ssg.sausagedutchpayapi.dutchpay.dto.request.DutchPaySaveRequest;
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
    @DisplayName("함께쓱정산 중복 생성")
    public void testSaveDutchPayDuplicate() {

        // given
        DutchPaySaveRequest request = DutchPaySaveRequest.builder()
                .cartShareOrdId(2L)
                .build();

        dutchPayService.saveDutchPay(request);

        // when, then
        Assertions.assertThrows(ConflictException.class, () -> {
            dutchPayService.saveDutchPay(request);
        });

    }


}
