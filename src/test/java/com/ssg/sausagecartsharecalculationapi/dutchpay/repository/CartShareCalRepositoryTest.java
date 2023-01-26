package com.ssg.sausagecartsharecalculationapi.dutchpay.repository;

import com.ssg.sausagecartsharecalculationapi.dutchpay.entity.DutchPay;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartShareCalRepositoryTest {

    @Autowired
    private CartShareCalRepository cartShareCalRepository;

    @Test
    @DisplayName("공유장바구니주문 id로 함께쓱정산 조회")
    public void testFindDutchPayByCartShareOrdId() {

        // given
        DutchPay dutchPay = DutchPay.builder()
                .dutchPayId(1L)
                .cartShareOrdId(2L)
                .build();
        cartShareCalRepository.save(dutchPay);

        // when
        DutchPay response = cartShareCalRepository.findByCartShareOrdId(2L)
                .orElseThrow(() -> new IllegalArgumentException("Test Failed"));

        // then
        Assertions.assertEquals(1L, response.getDutchPayId() );

    }


}
