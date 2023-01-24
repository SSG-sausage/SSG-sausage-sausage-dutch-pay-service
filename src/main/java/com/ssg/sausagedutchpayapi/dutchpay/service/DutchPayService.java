package com.ssg.sausagedutchpayapi.dutchpay.service;

import com.ssg.sausagedutchpayapi.common.client.internal.InternalMemberApiClient;
import com.ssg.sausagedutchpayapi.common.client.internal.InternalOrderApiClient;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.MbrFindInfo;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrdFindCartShareOrdDetailResponse;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrdFindCartShareOrdShppInfo;
import com.ssg.sausagedutchpayapi.common.client.internal.dto.response.OrdFindCartShareOrdResponse;
import com.ssg.sausagedutchpayapi.common.exception.ConflictException;
import com.ssg.sausagedutchpayapi.common.exception.ErrorCode;
import com.ssg.sausagedutchpayapi.common.exception.ForbiddenException;
import com.ssg.sausagedutchpayapi.common.exception.NotFoundException;
import com.ssg.sausagedutchpayapi.dutchpay.dto.request.DutchPayDtlUpdateRequest;
import com.ssg.sausagedutchpayapi.dutchpay.dto.request.DutchPaySaveRequest;
import com.ssg.sausagedutchpayapi.dutchpay.dto.response.DutchPayCalcResponse;
import com.ssg.sausagedutchpayapi.dutchpay.dto.response.DutchPayDtlCalcInfo;
import com.ssg.sausagedutchpayapi.dutchpay.dto.response.DutchPayDtlFindInfo;
import com.ssg.sausagedutchpayapi.dutchpay.dto.response.DutchPayFindResponse;
import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPay;
import com.ssg.sausagedutchpayapi.dutchpay.entity.DutchPayDtl;
import com.ssg.sausagedutchpayapi.dutchpay.repository.DutchPayDtlRepository;
import com.ssg.sausagedutchpayapi.dutchpay.repository.DutchPayRepository;
import java.util.List;
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
    public void saveDutchPay(DutchPaySaveRequest request) {

        OrdFindCartShareOrdResponse cartShareResponse = internalOrderApiClient.findCartShareOrd(
                request.getCartShareOrdId()).getBody().getData();

        validateDuplicateDutchPay(request.getCartShareOrdId());

        DutchPay dutchPay = dutchPayRepository.save(
                DutchPay.newInstance(request.getCartShareOrdId(), cartShareResponse.getMastrMbrId(),
                        cartShareResponse.getPaymtAmt()));

        dutchPayDtlRepository.saveAll(
                cartShareResponse.getMbrIdList().stream().map(id -> DutchPayDtl.newInstance(dutchPay, id))
                        .collect(Collectors.toList()));

    }

    public DutchPayFindResponse findDutchPay(Long mbrId, Long cartShareOrdId) {

        DutchPay dutchPay = findDutchPayById(cartShareOrdId);

        HashMap<Long, MbrFindInfo> mbrInfoResponse = internalMemberApiClient.findMbrList(
                dutchPay.getDutchPayDtlList().stream().map(dutchPayDtl -> dutchPayDtl.getMbrId())
                        .collect(Collectors.toList())).getBody().getData();
        return DutchPayFindResponse.of(dutchPay, dutchPay.getDutchPayDtlList().stream()
                .map(dutchPayDtl -> DutchPayDtlFindInfo.of(dutchPayDtl,
                        mbrInfoResponse.get(dutchPayDtl.getMbrId()).getMbrNm(), mbrId, dutchPay.getMastrMbrId()))
                .collect(Collectors.toList()), mbrId);

    }

    @Transactional
    public void updateDutchPay(Long mbrId, Long dutchPayId, DutchPayDtlUpdateRequest request) {

        DutchPay dutchPay = findDutchPayById(dutchPayId);
        validateMaster(mbrId, dutchPay.getMastrMbrId());

        if (!dutchPay.isDutchPayStYn()) {
            dutchPay.start();
        }

        dutchPay.update(request);

        switch (request.getDutchPayOptCd()) {
            case SECTION:
                request.getDutchPayDtlList().stream().forEach(
                        dtlRequest -> findDutchPayDtlByMbrIdAndDutchPayId(dtlRequest.getMbrId(),
                                dutchPayId).updateOptSection(dtlRequest));
                break;
            case SPLIT:
                dutchPay.getDutchPayDtlList()
                        .forEach(dutchPayDtl -> dutchPayDtl.updateOptSplit(request.getDutchPayDtlAmt()));
                break;
            case INPUT:
                request.getDutchPayDtlList().stream().forEach(
                        dtlRequest -> findDutchPayDtlByMbrIdAndDutchPayId(dtlRequest.getMbrId(),
                                dutchPayId).updateOptInput(dtlRequest));
                break;
        }

    }

    public DutchPayCalcResponse calcDutchPayBySection(Long dutchPayId) {
        DutchPay dutchPay = findDutchPayById(dutchPayId);

        OrdFindCartShareOrdDetailResponse ordResponse = internalOrderApiClient.findCartShareOrdDetail(
                dutchPay.getCartShareOrdId()).getBody().getData();

        HashMap<Long, MbrFindInfo> mbrInfoResponse = internalMemberApiClient.findMbrList(
                dutchPay.getDutchPayDtlList().stream().map(dutchPayDtl -> dutchPayDtl.getMbrId())
                        .collect(Collectors.toList())).getBody().getData();

        int mbrNum = dutchPay.getDutchPayDtlList().size();
        int commQt = getQt(ordResponse.getCommAmt(), mbrNum);
        int rmd = getRmd(ordResponse.getCommAmt(), mbrNum);

        List<DutchPayDtlCalcInfo> infoList = ordResponse.getOrdInfoList().stream()
                .map(info -> DutchPayDtlCalcInfo.of(info.getMbrId(), mbrInfoResponse.get(info.getMbrId()).getMbrNm(),
                        info.getOrdAmt(), commQt, dutchPay.getMastrMbrId())).collect(Collectors.toList());

        for (OrdFindCartShareOrdShppInfo shppInfo : ordResponse.getShppInfoList()) {
            rmd += getRmd(shppInfo.getShppCst(), shppInfo.getMbrIdList().size());
            int shppQt = getQt(shppInfo.getShppCst(), shppInfo.getMbrIdList().size());
            infoList.forEach(info -> {
                if (shppInfo.getMbrIdList().contains(info.getMbrId())) {
                    info.addDutchPayDtlAmt(shppQt);
                    info.addShppAmt(shppQt);
                }
            });
        }
        ;

        return DutchPayCalcResponse.of(dutchPay.getDutchPayId(), rmd, dutchPay.getPaymtAmt(), infoList);
    }


    @Transactional
    public void updateCmplYn(Long mbrId, Long dutchPayId, Long dtlMbrId) {
        DutchPay dutchPay = findDutchPayById(dutchPayId);
        validateMaster(mbrId, dutchPay.getMastrMbrId());

        DutchPayDtl dutchPayDtl = findDutchPayDtlByMbrIdAndDutchPayId(dtlMbrId, dutchPayId);
        dutchPayDtl.updateDutchPayCmplYn();
    }

    private int getQt(int x, int y) {
        if (x <= 0 || y <= 0) {
            return 0;
        }
        return Math.floorDiv(x, y);
    }

    private int getRmd(int x, int y) {
        if (x <= 0 || y <= 0) {
            return 0;
        }
        return Math.floorMod(x, y);
    }

    private void validateMaster(Long mbrId, Long masterId) {

        if (!mbrId.equals(masterId)) {
            throw new ForbiddenException(String.format("정산 변경 권한이 없습니다."),
                    ErrorCode.FORBIDDEN_DUTCH_PAY_UPDATE_EXCEPTION);
        }
    }

    private void validateDuplicateDutchPay(Long cartShareOrdId) {

        dutchPayRepository.findByCartShareOrdId(cartShareOrdId).ifPresent(x -> {
            throw new ConflictException(String.format("해당 공유 장바구니 주문 (%s) 에 대한 정산이 존재합니다.", cartShareOrdId),
                    ErrorCode.CONFLICT_DUTCH_PAY_EXCEPTION);
        });

    }

    private DutchPay findDutchPayById(Long dutchPayId) {
        return dutchPayRepository.findById(dutchPayId).orElseThrow(
                () -> new NotFoundException(String.format("존재하지 않는 정산 (%s) 입니다.", dutchPayId),
                        ErrorCode.NOT_FOUND_DUTCH_PAY_EXCEPTION));
    }

    private DutchPay findDutchPayByCartShareOrdId(Long cartShareOrdId) {

        return dutchPayRepository.findByCartShareOrdId(cartShareOrdId).orElseThrow(
                () -> new NotFoundException(String.format("해당 공유 장바구니 주문  (%s) 에 대한 정산이 존재하지 않습니다.", cartShareOrdId),
                        ErrorCode.NOT_FOUND_DUTCH_PAY_EXCEPTION));
    }

    private DutchPayDtl findDutchPayDtlByMbrIdAndDutchPayId(Long mbrId, Long dutchPayId) {

        return dutchPayDtlRepository.findDutchPayDtlByMbrIdAndDutchPayDutchPayId(mbrId, dutchPayId).orElseThrow(
                () -> new NotFoundException(String.format("존재하지 않는 정산 세부 (%s) 입니다.", mbrId),
                        ErrorCode.NOT_FOUND_DUTCH_PAY_DTL_EXCEPTION));

    }


}
