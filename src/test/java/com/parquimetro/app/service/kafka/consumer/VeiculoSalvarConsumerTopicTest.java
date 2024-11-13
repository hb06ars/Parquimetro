package com.parquimetro.app.service.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.parquimetro.domain.useCase.VeiculoSalvarUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.support.Acknowledgment;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;

class VeiculoSalvarConsumerTopicTest {

    @Mock
    VeiculoSalvarUseCase useCase;

    @Mock
    Acknowledgment acknowledgment;

    @InjectMocks
    VeiculoSalvarConsumerTopic veiculoSalvarConsumerTopic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConsume() throws JsonProcessingException {
        veiculoSalvarConsumerTopic.consume("message", acknowledgment);
        verify(useCase).execute(anyString());
        verify(acknowledgment).acknowledge();
    }
}
