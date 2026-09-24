package com.rytech.agendadortarefas.infrastructure.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("mensageria_falha")
public class MensageriaFalhaEntity {

    @Id
    private String id;
    private String topico;
    private String mensagem;
    private String stackTrace;
    private LocalDateTime dataFalha;

}
