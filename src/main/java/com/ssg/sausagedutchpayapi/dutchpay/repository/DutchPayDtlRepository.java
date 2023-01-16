package com.ssg.sausagedutchpayapi.dutchpay.repository;

import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPayDtl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DutchPayDtlRepository extends JpaRepository<DutchPayDtl, Long> {

}
