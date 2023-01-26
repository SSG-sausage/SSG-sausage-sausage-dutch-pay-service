package com.ssg.sausagecartsharecalculationapi.cartsharecal.service;

import com.ssg.sausagecartsharecalculationapi.common.client.internal.MbrApiClient;
import com.ssg.sausagecartsharecalculationapi.common.client.internal.CartShareOrdApiClient;
import com.ssg.sausagecartsharecalculationapi.common.client.internal.dto.response.CartShareOrdFindDetailForCartShareCal;
import com.ssg.sausagecartsharecalculationapi.common.client.internal.dto.response.CartShareOrdFindForCartShareCal;
import com.ssg.sausagecartsharecalculationapi.common.client.internal.dto.response.MbrInfo;
import com.ssg.sausagecartsharecalculationapi.common.client.internal.dto.response.CartShareOrdShppInfo;
import com.ssg.sausagecartsharecalculationapi.common.exception.ConflictException;
import com.ssg.sausagecartsharecalculationapi.common.exception.ErrorCode;
import com.ssg.sausagecartsharecalculationapi.common.exception.ForbiddenException;
import com.ssg.sausagecartsharecalculationapi.common.exception.NotFoundException;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.request.CartShareCalSaveRequest;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.request.CartShareCalUpdateRequest;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.response.CartShareCalSaveResponse;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.response.CartShareCalFindCalResponse;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.response.CartShareCalDtlFindCalInfo;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.response.CartShareCalDtlFindInfo;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.response.CartShareCalFindResponse;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.entity.CartShareCal;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.entity.CartShareCalDtl;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.repository.CartShareCalDtlRepository;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.repository.CartShareCalRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartShareCalService {

    private final CartShareCalRepository cartShareCalRepository;
    private final CartShareCalDtlRepository cartShareCalDtlRepository;
    private final CartShareOrdApiClient cartShareOrdApiClient;
    private final MbrApiClient mbrApiClient;

    @Transactional
    public CartShareCalSaveResponse saveCartShareCal(CartShareCalSaveRequest request) {

        CartShareOrdFindForCartShareCal cartShareResponse = cartShareOrdApiClient.findCartShareOrdForCartShareCal(
                request.getCartShareOrdId()).getData();

        validateDuplicateCartShareCal(request.getCartShareOrdId());

        CartShareCal cartShareCal = cartShareCalRepository.save(
                CartShareCal.newInstance(request.getCartShareOrdId(),
                        cartShareResponse.getMastrMbrId(),
                        cartShareResponse.getTtlPaymtAmt()));

        cartShareCalDtlRepository.saveAll(
                cartShareResponse.getMbrIdList().stream()
                        .map(mbrId -> CartShareCalDtl.newInstance(cartShareCal, mbrId))
                        .collect(Collectors.toList()));
        return CartShareCalSaveResponse.of(cartShareCal);
    }

    public CartShareCalFindResponse findCartShareCal(Long mbrId, Long cartShareOrdId) {

        CartShareCal cartShareCal = findCartShareCalById(cartShareOrdId);

        HashMap<Long, MbrInfo> mbrMap = mbrApiClient.findMbrList(
                cartShareCal.getCartShareCalDtlList().stream()
                        .map(cartShareCalDtl -> cartShareCalDtl.getMbrId())
                        .collect(Collectors.toList())).getData().getMbrMap();

        return CartShareCalFindResponse.of(cartShareCal,
                cartShareCal.getCartShareCalDtlList().stream()
                        .map(cartShareCalDtl -> CartShareCalDtlFindInfo.of(cartShareCalDtl,
                                mbrMap.get(cartShareCalDtl.getMbrId()).getMbrNm(), mbrId,
                                cartShareCal.getMastrMbrId()))
                        .collect(Collectors.toList()), mbrId);

    }

    @Transactional
    public void updateCartShareCal(Long mbrId, Long cartShareCalId,
            CartShareCalUpdateRequest request) {

        CartShareCal cartShareCal = findCartShareCalById(cartShareCalId);
        validateMaster(mbrId, cartShareCal.getMastrMbrId());

        if (!cartShareCal.isCalStYn()) {
            cartShareCal.start();
        }

        cartShareCal.update(request);

        switch (request.getCalOptCd()) {
            case SECTION:
                request.getCartShareCalDtlList().stream().forEach(
                        dtlRequest -> findCartShareCalDtlByMbrIdAndCartShareCalId(
                                dtlRequest.getMbrId(),
                                cartShareCalId).updateCalDtlAmtOnOptSection(dtlRequest));
                break;
            case SPLIT:
                cartShareCal.getCartShareCalDtlList()
                        .forEach(cartShareCalDtl -> cartShareCalDtl.updateCalDtlAmt(
                                request.getCalAmt()));
                break;
            case INPUT:
                request.getCartShareCalDtlList().stream().forEach(
                        dtlRequest -> findCartShareCalDtlByMbrIdAndCartShareCalId(
                                dtlRequest.getMbrId(),
                                cartShareCalId).updateCalDtlAmt(dtlRequest.getCalDtlAmt()));
                break;
        }

    }

    public CartShareCalFindCalResponse calCartShareCalOnOptSection(Long cartShareCalId) {
        CartShareCal cartShareCal = findCartShareCalById(cartShareCalId);

        CartShareOrdFindDetailForCartShareCal ordResponse = cartShareOrdApiClient.findCartShareOrdDetailForCartShareCal(
                cartShareCal.getCartShareOrdId()).getData();

        HashMap<Long, MbrInfo> mbrMap = mbrApiClient.findMbrList(
                cartShareCal.getCartShareCalDtlList().stream().map(cartShareCalDtl -> cartShareCalDtl.getMbrId())
                        .collect(Collectors.toList())).getData().getMbrMap();

        int mbrNum = cartShareCal.getCartShareCalDtlList().size();
        int commQt = getQt(ordResponse.getCommAmt(), mbrNum);
        int calRmd = getRmd(ordResponse.getCommAmt(), mbrNum);

        List<CartShareCalDtlFindCalInfo> infoList = ordResponse.getOrdInfoList().stream()
                .map(info -> CartShareCalDtlFindCalInfo.of(info.getMbrId(),
                        mbrMap.get(info.getMbrId()).getMbrNm(),
                        info.getOrdAmt(), commQt, cartShareCal.getMastrMbrId()))
                .collect(Collectors.toList());

        for (CartShareOrdShppInfo shppInfo : ordResponse.getShppInfoList()) {
            calRmd += getRmd(shppInfo.getShppCst(), shppInfo.getMbrIdList().size());
            int shppQt = getQt(shppInfo.getShppCst(), shppInfo.getMbrIdList().size());
            infoList.forEach(info -> {
                if (shppInfo.getMbrIdList().contains(info.getMbrId())) {
                    info.addCalDtlAmt(shppQt);
                    info.addShppCst(shppQt);
                }
            });
        }
        ;

        return CartShareCalFindCalResponse.of(cartShareCal.getCartShareCalId(), calRmd, cartShareCal.getTtlPaymtAmt(),
                infoList);
    }


    @Transactional
    public void updateCmplYn(Long mbrId, Long cartShareCalId, Long dtlMbrId) {
        CartShareCal cartShareCal = findCartShareCalById(cartShareCalId);
        validateMaster(mbrId, cartShareCal.getMastrMbrId());

        CartShareCalDtl cartShareCalDtl = findCartShareCalDtlByMbrIdAndCartShareCalId(dtlMbrId, cartShareCalId);
        cartShareCalDtl.updateCalDtlCmplYn();
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
            throw new ForbiddenException(String.format("공유장바구니 정산 변경 권한이 없습니다."),
                    ErrorCode.FORBIDDEN_CART_SHARE_CAL_UPDATE_EXCEPTION);
        }
    }

    private void validateDuplicateCartShareCal(Long cartShareOrdId) {

        cartShareCalRepository.findByCartShareOrdId(cartShareOrdId).ifPresent(x -> {
            throw new ConflictException(
                    String.format("해당 공유 장바구니 주문 (%s) 에 대한 정산이 존재합니다.", cartShareOrdId),
                    ErrorCode.CONFLICT_CART_SHARE_CAL_EXCEPTION);
        });

    }

    private CartShareCal findCartShareCalById(Long cartShareOrdId) {
        return cartShareCalRepository.findById(cartShareOrdId).orElseThrow(
                () -> new NotFoundException(
                        String.format("존재하지 않는 공유장바구니 정산 (%s) 입니다.", cartShareOrdId),
                        ErrorCode.NOT_FOUND_CART_SHARE_CAL_EXCEPTION));
    }

    private CartShareCal findCartShareCalByCartShareOrdId(Long cartShareOrdId) {

        return cartShareCalRepository.findByCartShareOrdId(cartShareOrdId).orElseThrow(
                () -> new NotFoundException(
                        String.format("해당 공유 장바구니 주문 (%s) 에 대한 정산이 존재하지 않습니다.", cartShareOrdId),
                        ErrorCode.NOT_FOUND_CART_SHARE_CAL_EXCEPTION));
    }

    private CartShareCalDtl findCartShareCalDtlByMbrIdAndCartShareCalId(Long mbrId,
            Long cartShareCalId){

        return cartShareCalDtlRepository.findCartShareCalDtlByMbrIdAndCartShareCalCartShareCalId(mbrId,
                        cartShareCalId)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format("존재하지 않는 공유장바구니 정산 세부 (%s) 입니다.", mbrId),
                                ErrorCode.NOT_FOUND_CART_SHARE_CAL_DTL_EXCEPTION));

    }


}
