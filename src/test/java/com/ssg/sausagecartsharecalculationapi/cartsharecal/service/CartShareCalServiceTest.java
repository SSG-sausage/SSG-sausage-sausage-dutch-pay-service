package com.ssg.sausagecartsharecalculationapi.cartsharecal.service;

import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.request.CartShareCalSaveRequest;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.response.CartShareCalSaveResponse;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.entity.CalOptCd;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.entity.CartShareCal;
import com.ssg.sausagecartsharecalculationapi.common.exception.ConflictException;
import java.time.LocalDateTime;
import java.util.Set;
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
    public void testSaveCartShareCal() {

        // given
        CartShareCalSaveRequest request = CartShareCalSaveRequest.builder()
                .cartShareOrdId(10L)
                .cartShareId(20L)
                .mastrMbrId(30L)
                .cartShareNm("")
                .cartShareOrdNo("")
                .mbrIdList(Set.of(30L, 31L))
                .build();

        // when
        CartShareCalSaveResponse response = cartShareCalService.saveCartShareCal(
                request);

        // then
        Assertions.assertEquals(1L, response.getCartShareCalId());

    }

    @Test
    @Transactional
    @DisplayName("공유장바구니 정산 중복 생성 에러")
    public void testSaveCartShareCalDuplicate() {

        // given
        CartShareCalSaveRequest request = CartShareCalSaveRequest.builder()
                .cartShareOrdId(10L)
                .cartShareId(20L)
                .mastrMbrId(30L)
                .cartShareNm("")
                .cartShareOrdNo("")
                .mbrIdList(Set.of(30L, 31L))
                .build();

        cartShareCalService.saveCartShareCal(request);

        // when, then
        Assertions.assertThrows(ConflictException.class, () -> {
            cartShareCalService.saveCartShareCal(request);
        });

    }


}
