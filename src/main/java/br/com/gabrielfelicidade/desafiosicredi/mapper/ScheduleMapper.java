package br.com.gabrielfelicidade.desafiosicredi.mapper;

import br.com.gabrielfelicidade.desafiosicredi.dto.CreateScheduleRequest;
import br.com.gabrielfelicidade.desafiosicredi.dto.Schedule;
import br.com.gabrielfelicidade.desafiosicredi.dto.ScheduleStatus;
import br.com.gabrielfelicidade.desafiosicredi.entity.ScheduleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    Schedule toSchedule(final ScheduleEntity schedule);

    ScheduleEntity toEntity(final Schedule schedule);

    default ScheduleEntity toEntity(final CreateScheduleRequest request) {
        return ScheduleEntity.builder()
                .description(request.getDescription())
                .status(ScheduleStatus.WAITING_RESULT)
                .build();
    }
}