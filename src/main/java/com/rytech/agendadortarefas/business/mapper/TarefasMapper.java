package com.rytech.agendadortarefas.business.mapper;

import com.rytech.agendadortarefas.business.dto.TarefasDTO;
import com.rytech.agendadortarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefasMapper {

    TarefasEntity paraTarefasEntity(TarefasDTO dto);

    TarefasDTO paraTarefasDTO(TarefasEntity entity);

}
