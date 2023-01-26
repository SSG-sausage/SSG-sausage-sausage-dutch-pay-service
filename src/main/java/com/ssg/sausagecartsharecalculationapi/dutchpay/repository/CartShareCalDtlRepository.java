package com.ssg.sausagecartsharecalculationapi.dutchpay.repository;

import com.ssg.sausagecartsharecalculationapi.dutchpay.entity.CartShareCalDtl;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartShareCalDtlRepository extends JpaRepository<CartShareCalDtl, Long> {

    Optional<CartShareCalDtl> findCartShareCalDtlByMbrIdAndCartShareCalCartShareCalId(Long mbrId, Long cartShareCalId);
}
