package com.rytech.agendadortarefas.business.mapper;

import com.rytech.agendadortarefas.business.dto.TarefasDTORecord;
import com.rytech.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    TarefasEntity paraTarefasEntity(TarefasDTORecord dto);


    TarefasDTORecord paraTarefasDTORecord(TarefasEntity entity);

    List<TarefasEntity> paraListaTarefasEntity(List<TarefasDTORecord> dtosRecord);


    List<TarefasDTORecord> paraListaTarefasDTORecord(List<TarefasEntity> entitiesList);

}
