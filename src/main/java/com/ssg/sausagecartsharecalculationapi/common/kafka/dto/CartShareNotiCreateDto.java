package com.ssg.sausagecartsharecalculationapi.common.kafka.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CartShareNotiCreateDto {

    @Schema(description = "멤버 ID")
    private Long mbrId;

    @Schema(description = "알림타입코드")
    private String notiCd;

    @Schema(description = "알림내용")
    private String cartShareNotiCntt;

    public static CartShareNotiCreateDto of(Long mbrId, String notiCd, String cartShareNotiCntt){
        return CartShareNotiCreateDto.builder()
                .mbrId(mbrId)
                .notiCd(notiCd)
                .cartShareNotiCntt(cartShareNotiCntt)
                .build();
    }
}

