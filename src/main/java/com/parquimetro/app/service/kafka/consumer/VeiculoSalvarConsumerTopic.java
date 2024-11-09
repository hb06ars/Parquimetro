package com.parquimetro.app.service.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.parquimetro.domain.useCase.VeiculoSalvarUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VeiculoSalvarConsumerTopic {

    @Autowired
    VeiculoSalvarUseCase useCase;

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message, Acknowledgment acknowledgment) throws JsonProcessingException {
        useCase.execute(message);
        acknowledgment.acknowledge();
    }
}
