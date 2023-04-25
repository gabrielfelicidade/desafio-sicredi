package br.com.gabrielfelicidade.desafiosicredi.service;

import br.com.gabrielfelicidade.desafiosicredi.dto.CreateScheduleRequest;
import br.com.gabrielfelicidade.desafiosicredi.dto.ScheduleStatus;
import br.com.gabrielfelicidade.desafiosicredi.entity.ScheduleEntity;
import br.com.gabrielfelicidade.desafiosicredi.mapper.ScheduleMapper;
import br.com.gabrielfelicidade.desafiosicredi.repository.ScheduleRepository;
import br.com.gabrielfelicidade.desafiosicredi.service.impl.ScheduleServiceImpl;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ScheduleMapper scheduleMapper;

    @Mock
    private VoteService voteService;

    @Captor
    private ArgumentCaptor<ScheduleEntity> captor;

    private EasyRandom random = new EasyRandom();

    @Test
    void shouldCreateSchedule() {
        final var request = random.nextObject(CreateScheduleRequest.class);
        final var entity = random.nextObject(ScheduleEntity.class);

        when(scheduleMapper.toEntity(request)).thenReturn(entity);

        scheduleService.createSchedule(request);

        verify(scheduleRepository).save(entity);
    }

    @Nested
    class UpdateSchedule {

        @Test
        void shouldUpdateScheduleStatusToRejectedWhenRejectionVotesAreMajority() {
            final var scheduleId = random.nextObject(UUID.class);
            final var schedule = random.nextObject(ScheduleEntity.builder().getClass()).id(scheduleId).build();

            when(voteService.getScheduleAcceptanceVotes(scheduleId)).thenReturn(0L);
            when(voteService.getScheduleRejectionVotes(scheduleId)).thenReturn(1L);
            when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));

            scheduleService.updateScheduleStatus(scheduleId);

            verify(scheduleRepository).save(captor.capture());

            final var updatedSchedule = captor.getValue();

            assertEquals(schedule.withStatus(ScheduleStatus.REJECTED), updatedSchedule);
        }

        @Test
        void shouldUpdateScheduleStatusToAcceptedWhenAcceptanceVotesAreMajority() {
            final var scheduleId = random.nextObject(UUID.class);
            final var schedule = random.nextObject(ScheduleEntity.builder().getClass()).id(scheduleId).build();

            when(voteService.getScheduleAcceptanceVotes(scheduleId)).thenReturn(1L);
            when(voteService.getScheduleRejectionVotes(scheduleId)).thenReturn(0L);
            when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));

            scheduleService.updateScheduleStatus(scheduleId);

            verify(scheduleRepository).save(captor.capture());

            final var updatedSchedule = captor.getValue();

            assertEquals(schedule.withStatus(ScheduleStatus.APPROVED), updatedSchedule);
        }

        @Test
        void shouldUpdateScheduleStatusToDrawWhenAcceptanceVotesAreEquals() {
            final var scheduleId = random.nextObject(UUID.class);
            final var schedule = random.nextObject(ScheduleEntity.builder().getClass()).id(scheduleId).build();

            when(voteService.getScheduleAcceptanceVotes(scheduleId)).thenReturn(0L);
            when(voteService.getScheduleRejectionVotes(scheduleId)).thenReturn(0L);
            when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));

            scheduleService.updateScheduleStatus(scheduleId);

            verify(scheduleRepository).save(captor.capture());

            final var updatedSchedule = captor.getValue();

            assertEquals(schedule.withStatus(ScheduleStatus.DRAW), updatedSchedule);
        }
    }
}