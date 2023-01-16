package com.ssg.sausagedutchpayapi.dutchpay.service;

import com.ssg.sausagedutchpayapi.common.client.internal.InternalMemberApiClient;
import com.ssg.sausagedutchpayapi.common.client.internal.InternalOrderApiClient;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.MbrFindInfo;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrdFindCartShareOrdResponse;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrdFindCartShareOrdShppInfo;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrdFindCartShareResponse;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrdFindTotalPriceResponse;
import com.ssg.sausagedutchpayapi.common.exception.ConflictException;
import com.ssg.sausagedutchpayapi.common.exception.ErrorCode;
import com.ssg.sausagedutchpayapi.common.exception.ForbiddenException;
import com.ssg.sausagedutchpayapi.common.exception.NotFoundException;
import com.ssg.sausagedutchpayapi.common.exception.ValidationException;
import com.ssg.sausagedutchpayapi.dutchpay.dto.request.DutchPayDtlUpdateRequest;
import com.ssg.sausagedutchpayapi.dutchpay.dto.response.DutchPayCalcResponse;
import com.ssg.sausagedutchpayapi.dutchpay.dto.response.DutchPayDtlCalcInfo;
import com.ssg.sausagedutchpayapi.dutchpay.dto.response.DutchPayDtlFindInfo;
import com.ssg.sausagedutchpayapi.dutchpay.dto.response.DutchPayFindResponse;
import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPay;
import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPayDtl;
import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPayOptCd;
import com.ssg.sausagedutchpayapi.dutchpay.repository.DutchPayDtlRepository;
import com.ssg.sausagedutchpayapi.dutchpay.repository.DutchPayRepository;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DutchPayService {

    private final DutchPayRepository dutchPayRepository;
    private final DutchPayDtlRepository dutchPayDtlRepository;
    private final InternalOrderApiClient internalOrderApiClient;

    private final InternalMemberApiClient internalMemberApiClient;

    @Transactional
    public void saveDutchPay(Long mbrId, Long cartShareOrdId) {

        OrdFindCartShareResponse cartShareResponse = internalOrderApiClient.findCartShareByCartShareOrdId(
                cartShareOrdId).getBody().getData();

        validateMaster(mbrId, cartShareResponse.getMasterId(), cartShareOrdId);

        validateDuplicateDutchPay(cartShareOrdId);

        DutchPay dutchPay = dutchPayRepository.save(DutchPay.newInstance(cartShareOrdId));

        dutchPayDtlRepository.saveAll(cartShareResponse.getMbrIdList().stream().map(id -> {
            return DutchPayDtl.newInstance(dutchPay, id);
        }).collect(Collectors.toList()));
    }

    public DutchPayFindResponse findDutchPay(Long cartShareOrdId) {

        HashMap<Long, MbrFindInfo> mbrInfoResponse = internalMemberApiClient.findMbrList(
                Arrays.asList(1L, 2L)).getBody().getData();
        DutchPay dutchPay = findDutchPayByCartShareOrdId(cartShareOrdId);
        return DutchPayFindResponse.of(dutchPay, dutchPay.getDutchPayDtlList().stream()
                .map(dutchPayDtl -> DutchPayDtlFindInfo.of(dutchPayDtl,
                        mbrInfoResponse.get(dutchPayDtl.getMbrId()).getMbrNm())).collect(
                        Collectors.toList()));
    }

    @Transactional
    public void updateDutchPayDtl(Long mbrId, Long dutchPayId, DutchPayDtlUpdateRequest request) {

        validateMaster(mbrId, dutchPayId);

        request.getDutchPayDtlUpdateInfoList().stream().forEach(req ->
                findDutchPayDtlByMbrIdAndDutchPayId(req.getMbrId(), dutchPayId)
                        .updateDutchPayDtlAmt(req.getDutchPayDtlAmt())
        );
    }

    public DutchPayCalcResponse calcDutchPay(Long dutchPayId, DutchPayOptCd dutchPayOptCd) {

        DutchPay dutchPay = findDutchPayById(dutchPayId);
        switch (dutchPayOptCd) {
            case DIVIDE_ALL:
                return calcDivideAll(dutchPay);
            case PERSONAL:
                return calcPersonal(dutchPay);
        }
        throw new ValidationException(String.format("%s는 존재하지 않는 함께쓱정산 옵션 입니다.", dutchPayOptCd),
                ErrorCode.VALIDATION_DUTCH_PAY_OPT_CD_VALUE_EXCEPTION);

    }

    private DutchPayCalcResponse calcDivideAll(DutchPay dutchPay) {
        // TODO: order-api response

        OrdFindTotalPriceResponse totalPriceResponse = internalOrderApiClient.findCartShareOrdTotalPrice(
                dutchPay.getCartShareOrdId()).getBody().getData();

        int mbrNum = dutchPay.getDutchPayDtlList().size();

        return DutchPayCalcResponse.of(
                dutchPay.getDutchPayId(),
                mod(totalPriceResponse.getTotalPrice(), mbrNum),
                div(totalPriceResponse.getTotalPrice(), mbrNum),
                dutchPay.getDutchPayDtlList()

        );
    }

    private DutchPayCalcResponse calcPersonal(DutchPay dutchPay) {

        OrdFindCartShareOrdResponse ordResponse = internalOrderApiClient.findCartShareOrd(
                dutchPay.getCartShareOrdId()).getBody().getData();
        int mbrNum = dutchPay.getDutchPayDtlList().size();
        int commQuot = div(ordResponse.getCommAmt(), mbrNum);
        int dutchPayRmd = mod(ordResponse.getCommAmt(), mbrNum);

        for (OrdFindCartShareOrdShppInfo shppInfo : ordResponse.getShppInfoList()) {
            dutchPayRmd +=
                    mod(shppInfo.getShppCst(), shppInfo.getMbrIdList().size());
            shppInfo.setShppQuot(
                    div(shppInfo.getShppCst(), shppInfo.getMbrIdList().size()));
        }

        DutchPayCalcResponse response = DutchPayCalcResponse.of(dutchPay.getDutchPayId(),
                dutchPayRmd,
                ordResponse.getOrdInfoList().stream().map(ordInfo -> {
                    int amt = commQuot;
                    for (OrdFindCartShareOrdShppInfo shppInfo : ordResponse.getShppInfoList()) {
                        if (shppInfo.getMbrIdList().contains(ordInfo.getMbrId())) {
                            amt += shppInfo.getShppQuot();
                        }
                    }
                    return DutchPayDtlCalcInfo.of(ordInfo, amt);
                }).collect(Collectors.toList())
        );

        return response;
    }

    private int div(int x, int y) {
        if (x <= 0 || y <= 0) {
            return 0;
        }
        return Math.floorDiv(x, y);
    }

    private int mod(int x, int y) {
        if (x <= 0 || y <= 0) {
            return 0;
        }
        return Math.floorMod(x, y);
    }


    private void validateMaster(Long mbrId, Long masterId, Long cartShareOrdId) {

        if (!mbrId.equals(masterId)) {
            throw new ForbiddenException(
                    String.format("해당 공유 장바구니 주문에 대한 함께쓱정산 생성 권한이 없습니다.", cartShareOrdId),
                    ErrorCode.FORBIDDEN_DUTCH_PAY_CREATE_EXCEPTION);
        }
    }

    private void validateMaster(Long mbrId, Long dutchPayId) {
        if (!mbrId.equals(findDutchPayById(dutchPayId).getDutchPayId())) {
            throw new ForbiddenException(
                    String.format("함께쓱정산 (%s) 수정 권한이 없습니다.", dutchPayId),
                    ErrorCode.FORBIDDEN_DUTCH_PAY_UPDATE_EXCEPTION);
        }

    }

    private void validateDuplicateDutchPay(Long cartShareOrdId) {

        dutchPayRepository.findByCartShareOrdId(cartShareOrdId).ifPresent(x -> {
            throw new ConflictException(
                    String.format("해당 공유 장바구니 주문 (%s) 에 대한 함께쓱정산이 존재합니다.", cartShareOrdId),
                    ErrorCode.CONFLICT_DUTCH_PAY_EXCEPTION);
        });

    }

    private DutchPay findDutchPayById(Long dutchPayId) {
        return dutchPayRepository.findById(dutchPayId).orElseThrow(
                () -> new NotFoundException(
                        String.format("존재하지 않는 함께쓱정산 (%s) 입니다.", dutchPayId),
                        ErrorCode.NOT_FOUND_DUTCH_PAY_EXCEPTION));
    }

    private DutchPay findDutchPayByCartShareOrdId(Long cartShareOrdId) {

        return dutchPayRepository.findByCartShareOrdId(cartShareOrdId).orElseThrow(
                () -> new NotFoundException(
                        String.format("해당 공유 장바구니 주문  (%s) 에 대한 함께쓱정산이 존재하지 않습니다.", cartShareOrdId),
                        ErrorCode.NOT_FOUND_DUTCH_PAY_EXCEPTION));
    }

    private DutchPayDtl findDutchPayDtlByMbrIdAndDutchPayId(Long mbrId, Long dutchPayId) {

        return dutchPayDtlRepository.findDutchPayDtlByMbrIdAndDutchPayDutchPayId(
                mbrId, dutchPayId).orElseThrow(
                () -> new NotFoundException(
                        String.format("존재하지 않는 함께쓱정산세부 (%s) 입니다.", mbrId),
                        ErrorCode.NOT_FOUND_DUTCH_PAY_DTL_EXCEPTION));

    }

    @Transactional
    public void updateCmplYn(Long dutchPayId, Long mbrId) {
        DutchPayDtl dutchPayDtl = findDutchPayDtlByMbrIdAndDutchPayId(mbrId, dutchPayId);
        dutchPayDtl.updateDutchPayCmplYn();
    }
}
