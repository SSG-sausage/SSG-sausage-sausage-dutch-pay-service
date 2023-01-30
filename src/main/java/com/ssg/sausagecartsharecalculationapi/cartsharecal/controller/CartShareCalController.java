package com.ssg.sausagecartsharecalculationapi.cartsharecal.controller;

import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.response.CartShareCalFindListResponse;
import com.ssg.sausagecartsharecalculationapi.common.config.resolver.MbrId;
import com.ssg.sausagecartsharecalculationapi.common.dto.ErrorResponse;
import com.ssg.sausagecartsharecalculationapi.common.dto.SuccessResponse;
import com.ssg.sausagecartsharecalculationapi.common.success.SuccessCode;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.request.CartShareCalSaveRequest;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.request.CartShareCalUpdateRequest;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.response.CartShareCalSaveResponse;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.response.CartShareCalFindCalResponse;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.response.CartShareCalFindResponse;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.service.CartShareCalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CartShareCal", description = "공유장바구니 정산")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CartShareCalController {

    private final CartShareCalService cartShareCalService;

    @Operation(summary = "[internal] 공유장바구니 정산 생성", responses = {
            @ApiResponse(responseCode = "201", description = "공유장바구니 정산 생성 성공입니다."),
            @ApiResponse(responseCode = "409", description = "해당 주문에 대한 공유장바구니 정산 데이터가 이미 존재합니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생하였습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),})
    @PostMapping("/cart-share-cal")
    public ResponseEntity<SuccessResponse<CartShareCalSaveResponse>> saveCartShareCal(
            @RequestBody CartShareCalSaveRequest request) {

        return SuccessResponse.success(SuccessCode.CREATED_SUCCESS,
                cartShareCalService.saveCartShareCal(request));
    }

    @Operation(summary = "[external] 공유장바구니 정산 조회", responses = {
            @ApiResponse(responseCode = "200", description = "공유장바구니 정산 조회 성공입니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 공유장바구니 정산 입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생하였습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),})
    @GetMapping("/cart-share-cal/{cartShareCalId}")
    public ResponseEntity<SuccessResponse<CartShareCalFindResponse>> findCartShareCal(
            @Parameter(in = ParameterIn.HEADER) @MbrId Long mbrId,
            @PathVariable Long cartShareCalId) {
        return SuccessResponse.success(SuccessCode.OK_SUCCESS,
                cartShareCalService.findCartShareCal(mbrId, cartShareCalId));
    }

    @Operation(summary = "[external] 공유장바구니 정산 리스트 조회", responses = {
            @ApiResponse(responseCode = "200", description = "공유장바구니 정산 리스트 조회 성공입니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생하였습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),})
    @GetMapping("/cart-share-cal")
    public ResponseEntity<SuccessResponse<List<CartShareCalFindListResponse>>> findCartShareCalList(
            @RequestParam Long cartShareId) {
        return SuccessResponse.success(SuccessCode.OK_SUCCESS,
                cartShareCalService.findCartShareCalList(cartShareId));
    }

    @Operation(summary = "[external] 공유장바구니 정산 수정", responses = {
            @ApiResponse(responseCode = "200", description = "공유장바구니 정산 수정 성공입니다."),
            @ApiResponse(responseCode = "403", description = "공유장바구니 정산 수정 권한이 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 공유장바구니 정산 입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생하였습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),})
    @PutMapping("/cart-share-cal/{cartShareCalId}")
    public ResponseEntity<SuccessResponse<String>> updateCartShareCal(
            @Parameter(in = ParameterIn.HEADER) @MbrId Long mbrId,
            @PathVariable Long cartShareCalId, @RequestBody CartShareCalUpdateRequest request) {
        cartShareCalService.updateCartShareCal(mbrId, cartShareCalId, request);
        return SuccessResponse.OK;
    }

    @Operation(summary = "[external] 공유장바구니 정산 금액 계산", responses = {
            @ApiResponse(responseCode = "200", description = "공유장바구니 정산 금액 계산 조회 성공입니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 공유장바구니 정산 입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생하였습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),})
    @GetMapping("/cart-share-cal/{cartShareCalId}/cal")
    public ResponseEntity<SuccessResponse<CartShareCalFindCalResponse>> calCartShareCalOnOptSection(
            @PathVariable Long cartShareCalId) {
        return SuccessResponse.success(SuccessCode.OK_SUCCESS,
                cartShareCalService.calCartShareCalOnOptSection(cartShareCalId));
    }

    @Operation(summary = "[external] 공유장바구니 정산 멤버 완료여부 변경", responses = {
            @ApiResponse(responseCode = "200", description = "공유장바구니 정산 멤버 완료여부 변경 성공입니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 공유장바구니 정산세부 입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생하였습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),})
    @PatchMapping("/cart-share-cal/{cartShareCalId}/mbr-id/{dtlMbrId}/cmpl")
    public ResponseEntity<SuccessResponse<String>> updateCmplYn(
            @Parameter(in = ParameterIn.HEADER) @MbrId Long mbrId,
            @PathVariable Long cartShareCalId, @PathVariable Long dtlMbrId) {
        cartShareCalService.updateCmplYn(mbrId, cartShareCalId, dtlMbrId);
        return SuccessResponse.OK;
    }

    @Operation(summary = "[external] 공유장바구니 정산 알림 생성", responses = {
            @ApiResponse(responseCode = "201", description = "공유장바구니 정산 알림 생성 성공입니다."),
            @ApiResponse(responseCode = "403", description = "공유장바구니 정산 알림 생성 권한이 없습니다..", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생하였습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),})
    @PostMapping("/cart-share-cal/{cartShareCalId}/noti")
    public ResponseEntity<SuccessResponse<String>> saveCartShareNoti(
            @Parameter(in = ParameterIn.HEADER) @MbrId Long mbrId,
            @PathVariable Long cartShareCalId) {
        cartShareCalService.saveCartShareNoti(mbrId, cartShareCalId);
        return SuccessResponse.CREATED;
    }

}
