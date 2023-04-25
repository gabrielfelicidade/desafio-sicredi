package br.com.gabrielfelicidade.desafiosicredi.mapper;

import br.com.gabrielfelicidade.desafiosicredi.dto.VotingSession;
import br.com.gabrielfelicidade.desafiosicredi.dto.VotingSessionFinishedEvent;
import br.com.gabrielfelicidade.desafiosicredi.dto.VotingSessionStatus;
import br.com.gabrielfelicidade.desafiosicredi.entity.ScheduleEntity;
import br.com.gabrielfelicidade.desafiosicredi.entity.VotingSessionEntity;
import org.mapstruct.Mapper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface VotingSessionMapper {

    VotingSessionFinishedEvent toEvent(final VotingSessionEntity votingSession);

    default VotingSession toVotingSession(final VotingSessionEntity votingSession) {
        return VotingSession.builder()
                .status(votingSession.getStatus())
                .endDate(votingSession.getEndDate())
                .id(votingSession.getId())
                .scheduleId(votingSession.getSchedule().getId())
                .build();
    }

    default VotingSessionEntity toEntity(final UUID scheduleId,
                                         final Duration duration) {
        return VotingSessionEntity.builder()
                .schedule(ScheduleEntity.builder().id(scheduleId).build())
                .endDate(LocalDateTime.now().plus(duration))
                .status(VotingSessionStatus.STARTED)
                .build();
    }
}
