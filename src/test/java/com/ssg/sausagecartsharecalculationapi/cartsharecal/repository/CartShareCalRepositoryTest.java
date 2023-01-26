package com.ssg.sausagecartsharecalculationapi.cartsharecal.repository;

import com.ssg.sausagecartsharecalculationapi.cartsharecal.entity.CalOptCd;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.entity.CartShareCal;
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
    @DisplayName("공유장바구니 주문 id로 공유장바구니 정산 조회")
    public void testFindDutchPayByCartShareOrdId() {

        // given
        CartShareCal cartShareCal = CartShareCal.newInstance(10L, 20L, 0);
        cartShareCalRepository.save(cartShareCal);

        // when
        CartShareCal response = cartShareCalRepository.findByCartShareOrdId(10L)
                .orElseThrow(() -> new IllegalArgumentException("Test Failed"));

        // then
        Assertions.assertEquals(1L, response.getCartShareCalId());

    }


}
