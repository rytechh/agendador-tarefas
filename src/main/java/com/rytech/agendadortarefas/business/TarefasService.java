package com.rytech.agendadortarefas.business;

import com.rytech.agendadortarefas.business.dto.TarefasDTORecord;
import com.rytech.agendadortarefas.business.mapper.TarefasConverter;
import com.rytech.agendadortarefas.business.mapper.TarefasUpdateConverter;
import com.rytech.agendadortarefas.infrastructure.entity.TarefasEntity;
import com.rytech.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.rytech.agendadortarefas.infrastructure.exceptions.ResourceNotFoundException;
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
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;
    private final TarefasUpdateConverter tarefasUpdateConverter;

    public TarefasDTORecord gravaTarefas(String token, TarefasDTORecord dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        TarefasDTORecord dtoFinal = new TarefasDTORecord(null, dto.nomeTarefa(),
                dto.descricao(), LocalDateTime.now(), dto.dataEvento(),
                email, null ,StatusNotificacaoEnum.PENDENTE);

        TarefasEntity entity = tarefasConverter.paraTarefasEntity(dtoFinal);

        return tarefasConverter.paraTarefasDTORecord(tarefasRepository.save(entity));

    }

    public List<TarefasDTORecord> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {

        return tarefasConverter.paraListaTarefasDTORecord(tarefasRepository.findByDataEventoBetweenAndStatusNotificacaoEnum
                (dataInicial, dataFinal, StatusNotificacaoEnum.PENDENTE));
    }

    public List<TarefasDTORecord> buscaTarefasPorEmail(String token) {

        String email = jwtUtil.extrairEmailToken(token.substring(7));

        return tarefasConverter.paraListaTarefasDTORecord(tarefasRepository.findByEmailUsuario(email));
    }

    public void deletaTarefaPorId(String id) {
        try {
            tarefasRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(
                    "Erro ao tentar deletar tarefa por id, id inexistente " + id + e.getCause());
        }
    }


    public TarefasDTORecord alteraStatus(StatusNotificacaoEnum status, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: " + id));
            entity.setStatusNotificacaoEnum(status);

            return tarefasConverter.paraTarefasDTORecord(tarefasRepository.save(entity));

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar tarefa por id, id inexistente " + e.getCause());
        }
    }

    public TarefasDTORecord updateTarefas(TarefasDTORecord dto, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id).orElseThrow(
                    ()
                            -> new ResourceNotFoundException("Tarefa não encontrada " + id));

            tarefasUpdateConverter.updateTarefas(dto, entity);

            return tarefasConverter.paraTarefasDTORecord(tarefasRepository.save(entity));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + e.getCause());
        }

    }
}
