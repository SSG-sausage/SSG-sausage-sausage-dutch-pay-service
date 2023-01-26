package com.ssg.sausagecartsharecalculationapi.dutchpay.repository;

import com.ssg.sausagecartsharecalculationapi.dutchpay.entity.CartShareCal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartShareCalRepository extends JpaRepository<CartShareCal, Long> {

    Optional<CartShareCal> findByCartShareOrdId(Long cartShareOrdId);
}
