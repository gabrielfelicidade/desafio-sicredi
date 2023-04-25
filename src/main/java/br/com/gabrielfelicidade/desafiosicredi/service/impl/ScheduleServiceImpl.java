package br.com.gabrielfelicidade.desafiosicredi.service.impl;

import br.com.gabrielfelicidade.desafiosicredi.dto.CreateScheduleRequest;
import br.com.gabrielfelicidade.desafiosicredi.dto.Schedule;
import br.com.gabrielfelicidade.desafiosicredi.dto.ScheduleStatus;
import br.com.gabrielfelicidade.desafiosicredi.entity.ScheduleEntity;
import br.com.gabrielfelicidade.desafiosicredi.exception.EntityNotFoundException;
import br.com.gabrielfelicidade.desafiosicredi.mapper.ScheduleMapper;
import br.com.gabrielfelicidade.desafiosicredi.repository.ScheduleRepository;
import br.com.gabrielfelicidade.desafiosicredi.service.ScheduleService;
import br.com.gabrielfelicidade.desafiosicredi.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final VoteService voteService;

    @Override
    public Schedule createSchedule(final CreateScheduleRequest request) {
        final var scheduleEntity = scheduleRepository.save(scheduleMapper.toEntity(request));

        return scheduleMapper.toSchedule(scheduleEntity);
    }

    @Override
    public void updateScheduleStatus(final UUID scheduleId) {
        final var schedule = findById(scheduleId);
        final var newScheduleStatus = buildStatusFromScheduleVotes(scheduleId);

        scheduleRepository.save(schedule.withStatus(newScheduleStatus));
    }

    private ScheduleEntity findById(final UUID scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(EntityNotFoundException::new);
    }

    private ScheduleStatus buildStatusFromScheduleVotes(final UUID scheduleId) {
        final var acceptanceVotes = voteService.getScheduleAcceptanceVotes(scheduleId);
        final var rejectionVotes = voteService.getScheduleRejectionVotes(scheduleId);
        final var votesDifference = Long.signum(acceptanceVotes - rejectionVotes);
        return switch (votesDifference) {
            case -1 -> ScheduleStatus.REJECTED;
            case 0 -> ScheduleStatus.DRAW;
            case 1 -> ScheduleStatus.APPROVED;
            default -> throw new IllegalStateException("Unexpected value: " + votesDifference);
        };
    }
}