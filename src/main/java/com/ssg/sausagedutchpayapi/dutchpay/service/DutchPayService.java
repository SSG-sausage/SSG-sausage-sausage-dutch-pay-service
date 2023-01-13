package com.ssg.sausagedutchpayapi.dutchpay.service;

import com.ssg.sausagedutchpayapi.common.client.internal.InternalOrderApiClient;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrderFindCartShareResponse;
import com.ssg.sausagedutchpayapi.common.exception.DuplicateException;
import com.ssg.sausagedutchpayapi.common.exception.ErrorCode;
import com.ssg.sausagedutchpayapi.common.exception.ForbiddenException;
import com.ssg.sausagedutchpayapi.common.exception.UnAuthorizedException;
import com.ssg.sausagedutchpayapi.dutchpay.dto.response.DutchPayFindResponse;
import com.ssg.sausagedutchpayapi.dutchpay.dto.response.DutchPaySaveResponse;
import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPay;
import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPayDtl;
import com.ssg.sausagedutchpayapi.dutchpay.repository.DutchPayDtlRepository;
import com.ssg.sausagedutchpayapi.dutchpay.repository.DutchPayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DutchPayService {

    private final DutchPayRepository dutchPayRepository;
    private final DutchPayDtlRepository dutchPayDtlRepository;
    private final InternalOrderApiClient internalOrderApiClient;

    @Transactional
    public DutchPaySaveResponse saveDutchPay(Long mbrId, Long cartShareOrdId) {

        OrderFindCartShareResponse response = internalOrderApiClient.findCartShareByCartShareOrdId(
                cartShareOrdId).getBody().getData();

        if (mbrId != response.getMasterId()) {
            throw new ForbiddenException(
                    String.format("해당 공유 장바구니 주문(ID:%s)에 대한 함께쓱정산 생성 권한이 없습니다.", cartShareOrdId),
                    ErrorCode.FORBIDDEN_DUTCH_PAY_CREATE_EXCEPTION);
        }

        dutchPayRepository.findByCartShareOrdId(cartShareOrdId).ifPresent(x -> {
            throw new DuplicateException(
                    String.format("해당 공유 장바구니 주문(ID:%s)에 대한 더치페이가 존재합니다.", cartShareOrdId),
                    ErrorCode.DUPLICATE_DUTCH_PAY_EXCEPTION);
        });

        DutchPay dutchPay = dutchPayRepository.save(DutchPay.newInstance(cartShareOrdId));

        dutchPayDtlRepository.saveAll(response.getMbrIdList().stream().map(id -> {
            return DutchPayDtl.newInstance(dutchPay, id);
        }).collect(Collectors.toList()));

        return DutchPaySaveResponse.of(dutchPay);
    }

}
