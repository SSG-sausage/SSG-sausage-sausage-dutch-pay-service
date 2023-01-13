package com.ssg.sausagedutchpayapi.dutchpay.repository;

import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DutchPayRepository extends JpaRepository<DutchPay, Long> {

    Optional<DutchPay> findByCartShareOrdId(Long cartShareOrdId);
}
