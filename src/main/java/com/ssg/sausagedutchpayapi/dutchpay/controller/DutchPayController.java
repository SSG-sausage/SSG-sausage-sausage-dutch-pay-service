package com.ssg.sausagedutchpayapi.dutchpay.controller;

import com.ssg.sausagedutchpayapi.common.config.resolver.MbrId;
import com.ssg.sausagedutchpayapi.common.dto.ErrorResponse;
import com.ssg.sausagedutchpayapi.common.dto.SuccessResponse;
import com.ssg.sausagedutchpayapi.dutchpay.service.DutchPayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "DutchPay", description = "함께쓱정산")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DutchPayController {

    private final DutchPayService dutchPayService;

    @Operation(summary = "함께쓱정산 생성", responses = {
            @ApiResponse(responseCode = "201", description = "함께쓱정산 생성 성공입니다."),
            @ApiResponse(responseCode = "403", description = "해당 주문에 대한 함께쓱정산 생성 권한이 없습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "해당 주문에 대한 함께쓱정산 데이터가 이미 존재합니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생하였습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/cart-share-order/{cartShareOrdId}/dutch-pay")
    public ResponseEntity<SuccessResponse<String>> saveDutchPay(
            @Parameter(in = ParameterIn.HEADER) @MbrId Long mbrId,
            @PathVariable Long cartShareOrdId) {
        dutchPayService.saveDutchPay(mbrId, cartShareOrdId);
        return SuccessResponse.CREATED;
    }

}
