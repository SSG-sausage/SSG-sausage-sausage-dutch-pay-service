package com.ssg.sausagedutchpayapi.dutchpay.repository;

import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPayDtl;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DutchPayDtlRepository extends JpaRepository<DutchPayDtl, Long> {

    Optional<DutchPayDtl> findDutchPayDtlByMbrIdAndDutchPayDutchPayId(Long mbrId, Long dutchPayId);
}
