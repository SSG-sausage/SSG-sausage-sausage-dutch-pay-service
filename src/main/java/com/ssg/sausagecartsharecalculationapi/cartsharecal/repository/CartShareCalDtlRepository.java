package com.ssg.sausagecartsharecalculationapi.cartsharecal.repository;

import com.ssg.sausagecartsharecalculationapi.cartsharecal.entity.CartShareCalDtl;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartShareCalDtlRepository extends JpaRepository<CartShareCalDtl, Long> {

    Optional<CartShareCalDtl> findByMbrIdAndCartShareCalCartShareCalId(Long mbrId, Long cartShareCalId);


    List<CartShareCalDtl> findAllByCartShareCalCartShareCalIdAndCalCmplYn(Long cartShareCalId, boolean calCmplYn);
}
