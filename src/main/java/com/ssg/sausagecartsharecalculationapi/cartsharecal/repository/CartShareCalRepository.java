package com.ssg.sausagecartsharecalculationapi.cartsharecal.repository;

import com.ssg.sausagecartsharecalculationapi.cartsharecal.entity.CartShareCal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartShareCalRepository extends JpaRepository<CartShareCal, Long> {

    Optional<CartShareCal> findByCartShareOrdId(Long cartShareOrdId);
}
