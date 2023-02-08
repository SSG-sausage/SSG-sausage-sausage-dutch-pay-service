package com.ssg.sausagecartsharecalculationapi.common.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.request.CartShareCalSaveRequest;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.service.CartShareCalService;
import com.ssg.sausagecartsharecalculationapi.common.kafka.constant.KafkaConstants;
import com.ssg.sausagecartsharecalculationapi.common.kafka.dto.CartShareCalRetryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ConsumeService {

    private final CartShareCalService cartShareCalService;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = KafkaConstants.KAFKA_CART_SHARE_CAL_SAVE_RETRY, groupId = KafkaConstants.CONSUMER_GROUP_ID)
    public void retryCartShareCal(String consumeMsg) throws JsonProcessingException{
        CartShareCalRetryDto cartShareCalRetryDto = objectMapper.readValue(consumeMsg, CartShareCalRetryDto.class);

        cartShareCalService.retrySaveCartShareCal(CartShareCalSaveRequest.of(cartShareCalRetryDto));
    }

}
