package com.rytech.agendadortarefas.infrastructure.messages.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rytech.agendadortarefas.business.TarefasService;
import com.rytech.agendadortarefas.business.dto.StatusTarefaDTO;
import com.rytech.agendadortarefas.infrastructure.entity.MensageriaFalhaEntity;
import com.rytech.agendadortarefas.infrastructure.repository.MensageriaFalhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final TarefasService tarefasService;
    private final MensageriaFalhaRepository repository;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 3000, multiplier = 2.0),
            autoCreateTopics = "true",
            dltStrategy = DltStrategy.FAIL_ON_ERROR
    )
    @KafkaListener(topics = "status-notificacao", groupId = "grupo-agendador", containerFactory = "kafkaListenerContainerFactory")
    public void consumirStatusTarefa(String mensagem) throws JsonProcessingException {

        StatusTarefaDTO dto = objectMapper.readValue(mensagem, StatusTarefaDTO.class);

        tarefasService.alteraStatus(dto.getStatusNotificacaoEnum(), dto.getId());
    }

    @DltHandler
    public void recuperarMensagemFalha(String mensagem,
                                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                       Throwable exception) {
        MensageriaFalhaEntity mensageriaFalha = MensageriaFalhaEntity.builder()
                .topico(topic)
                .dataFalha(LocalDateTime.now())
                .stackTrace(exception.getMessage())
                .mensagem(mensagem)
                .build();

        repository.save(mensageriaFalha);
    }

}
