package com.ssg.sausagecartsharecalculationapi.cartsharecal.repository;

import com.ssg.sausagecartsharecalculationapi.cartsharecal.entity.CartShareCalDtl;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartShareCalDtlRepository extends JpaRepository<CartShareCalDtl, Long> {

    Optional<CartShareCalDtl> findCartShareCalDtlByMbrIdAndCartShareCalCartShareCalId(Long mbrId, Long cartShareCalId);
}
