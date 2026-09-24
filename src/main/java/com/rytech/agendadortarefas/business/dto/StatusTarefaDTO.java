package com.rytech.agendadortarefas.business.dto;

import com.rytech.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusTarefaDTO {
    private String id;
    private StatusNotificacaoEnum statusNotificacaoEnum;
}
