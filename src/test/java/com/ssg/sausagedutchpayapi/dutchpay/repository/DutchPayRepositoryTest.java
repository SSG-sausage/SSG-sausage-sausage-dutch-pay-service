package com.ssg.sausagedutchpayapi.dutchpay.repository;

import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPay;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest

public class DutchPayRepositoryTest {

    @Autowired
    private DutchPayRepository dutchPayRepository;

    @Test
    @DisplayName("공유장바구니주문 id로 함께쓱정산 조회")
    public void testFindDutchPayByCartShareOrdId() {

        // given
        DutchPay dutchPay = DutchPay.builder()
                .dutchPayId(1L)
                .cartShareOrdId(2L)
                .build();
        dutchPayRepository.save(dutchPay);

        // when
        DutchPay response = dutchPayRepository.findByCartShareOrdId(2L)
                .orElseThrow(() -> new IllegalArgumentException("Test Failed"));

        // then
        Assertions.assertEquals(1L, response.getDutchPayId() );

    }


}
