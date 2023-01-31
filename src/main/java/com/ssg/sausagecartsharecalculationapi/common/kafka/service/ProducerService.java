package com.ssg.sausagecartsharecalculationapi.common.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.sausagecartsharecalculationapi.common.exception.ErrorCode;
import com.ssg.sausagecartsharecalculationapi.common.exception.InternalServerException;
import com.ssg.sausagecartsharecalculationapi.common.kafka.constant.KafkaConstants;
import com.ssg.sausagecartsharecalculationapi.common.kafka.dto.CartShareCalStartDto;
import com.ssg.sausagecartsharecalculationapi.common.kafka.dto.CartShareNotiCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void createCartShareNoti(Long mbrId, String notiCd, String cartShareNotiCntt) {
        produceKafkaMsg(KafkaConstants.KAFKA_CART_SHARE_NOTI_CREATE, CartShareNotiCreateDto.of(mbrId,notiCd,cartShareNotiCntt));
    }

    public void startCartShareCal(Long cartShareCalId) {
        produceKafkaMsg(KafkaConstants.KAFKA_CART_SHARE_CAL_START, CartShareCalStartDto.of(cartShareCalId));
    }


    public void produceKafkaMsg(String topicNm, Object object) {
        try {
            kafkaTemplate.send(topicNm, objectMapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            throw new InternalServerException("예상치 못한 서버 에러가 발생하였습니다.", ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
    }

}
