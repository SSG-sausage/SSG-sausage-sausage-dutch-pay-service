package com.ssg.sausagecartsharecalculationapi.cartsharecal.service;

import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.request.CartShareCalSaveRequest;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.response.CartShareCalSaveResponse;
import com.ssg.sausagecartsharecalculationapi.common.exception.ConflictException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CartShareCalServiceTest {

    @Autowired
    private CartShareCalService cartShareCalService;

    @Test
    @Transactional
    @DisplayName("공유장바구니 정산 생성")
    public void testSaveDutchPay() {

        // given
        CartShareCalSaveRequest request = CartShareCalSaveRequest.builder()
                .cartShareOrdId(10L)
                .build();

        // when
        CartShareCalSaveResponse response = cartShareCalService.saveCartShareCal(
                request);

        // then
        Assertions.assertEquals(1L, response.getCartShareCalId());

    }

    @Test
    @Transactional
    @DisplayName("함께쓱정산 중복 생성 에러")
    public void testSaveDutchPayDuplicate() {

        // given
        CartShareCalSaveRequest request = CartShareCalSaveRequest.builder()
                .cartShareOrdId(10L)
                .build();

        cartShareCalService.saveCartShareCal(request);

        // when, then
        Assertions.assertThrows(ConflictException.class, () -> {
            cartShareCalService.saveCartShareCal(request);
        });

    }


}
