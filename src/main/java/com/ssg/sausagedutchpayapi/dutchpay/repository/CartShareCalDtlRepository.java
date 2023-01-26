package com.ssg.sausagedutchpayapi.dutchpay.repository;

import com.ssg.sausagedutchpayapi.dutchpay.entity.CartShareCalDtl;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartShareCalDtlRepository extends JpaRepository<CartShareCalDtl, Long> {

    Optional<CartShareCalDtl> findCartShareCalDtlByMbrIdAndCartShareCalCartShareCalId(Long mbrId, Long cartShareCalId);
}
