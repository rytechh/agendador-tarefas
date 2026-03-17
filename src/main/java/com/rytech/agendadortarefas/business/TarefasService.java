package com.rytech.agendadortarefas.business;

import com.rytech.agendadortarefas.business.dto.TarefasDTO;
import com.rytech.agendadortarefas.business.mapper.TarefasMapper;
import com.rytech.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.rytech.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.rytech.agendadortarefas.infrastructure.repository.TarefasRepository;
import com.rytech.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {


    private final TarefasRepository tarefasRepository;
    private final TarefasMapper tarefasMapper;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravaTarefas(String token, TarefasDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefasMapper.paraTarefasEntity(dto);

        return tarefasMapper.paraTarefasDTO(tarefasRepository.save(entity));

    }

    public List<TarefasDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {

        return tarefasMapper.paraListaTarefasDTO(
                tarefasRepository.findByDataEventoBetween(dataInicial, dataFinal));
    }

    public List<TarefasDTO> buscaTarefasPorEmail(String token) {

        String email = jwtUtil.extrairEmailToken(token.substring(7));

        return tarefasMapper.paraListaTarefasDTO(
                tarefasRepository.findByEmailUsuario(email));
    }


}
