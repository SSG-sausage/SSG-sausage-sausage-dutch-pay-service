package com.ssg.sausagecartsharecalculationapi.cartsharecal.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ssg.sausagecartsharecalculationapi.cartsharecal.entity.CalOptCd;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.entity.CartShareCal;
import java.time.LocalDateTime;
import java.util.List;
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
        CartShareCal cartShareCal = CartShareCal.builder()
                .cartShareCalId(1L)
                .cartShareOrdId(10L)
                .cartShareId(20L)
                .mastrMbrId(30L)
                .cartShareNm("")
                .cartShareCalStDts(LocalDateTime.now())
                .cartShareOrdNo("")
                .calOptCd(CalOptCd.INPUT)
                .build();

        cartShareCalRepository.save(cartShareCal);

        // when
        CartShareCal response = cartShareCalRepository.findByCartShareOrdId(10L)
                .orElseThrow(() -> new IllegalArgumentException("Test Failed"));

        // then
        Assertions.assertEquals(1L, response.getCartShareCalId());

    }

    @Test
    @DisplayName("시작된 정산 리스트 조회")
    public void testFindAllByCartShareIdAndCalStYnOrderByCartShareCalStDtsDesc() {

        // given
        CartShareCal cartShareCal1 = CartShareCal.builder()
                .cartShareCalId(1L)
                .cartShareOrdId(10L)
                .cartShareId(20L)
                .mastrMbrId(30L)
                .cartShareNm("")
                .calStYn(false)
                .cartShareCalStDts(null)
                .cartShareOrdNo("")
                .calOptCd(CalOptCd.INPUT)
                .build();

        CartShareCal cartShareCal2 = CartShareCal.builder()
                .cartShareCalId(2L)
                .cartShareOrdId(10L)
                .cartShareId(20L)
                .mastrMbrId(30L)
                .cartShareNm("")
                .calStYn(true)
                .cartShareCalStDts(LocalDateTime.now())
                .cartShareOrdNo("")
                .calOptCd(CalOptCd.INPUT)
                .build();

        CartShareCal cartShareCal3 = CartShareCal.builder()
                .cartShareCalId(3L)
                .cartShareOrdId(10L)
                .cartShareId(20L)
                .mastrMbrId(30L)
                .cartShareNm("")
                .calStYn(true)
                .cartShareCalStDts(LocalDateTime.now().minusDays(2))
                .cartShareOrdNo("")
                .calOptCd(CalOptCd.INPUT)
                .build();

        cartShareCalRepository.save(cartShareCal1);
        cartShareCalRepository.save(cartShareCal2);
        cartShareCalRepository.save(cartShareCal3);

        // when
        List<CartShareCal> cartShareCalList = cartShareCalRepository.findAllByCartShareIdAndCalStYnOrderByCartShareCalStDtsDesc(
                20L, true);

        // then
        assertAll(
                () -> assertEquals(2, cartShareCalList.size()),
                () -> assertEquals(2L, cartShareCalList.get(0).getCartShareCalId()),
                () -> assertEquals(3L, cartShareCalList.get(1).getCartShareCalId())
                );
    }

}
