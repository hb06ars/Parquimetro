package com.parquimetro.app.service.kafka.producer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class KafkaProducerTest {
    @Mock
    KafkaTemplate<String, String> kafkaTemplate;
    @InjectMocks
    KafkaProducer kafkaProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMessage() {
        when(kafkaTemplate.send(anyString(), anyString())).thenReturn(null);
        kafkaProducer.sendMessage("topico", "message");
    }
}