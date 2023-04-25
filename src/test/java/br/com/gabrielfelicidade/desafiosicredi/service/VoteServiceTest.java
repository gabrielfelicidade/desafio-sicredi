package br.com.gabrielfelicidade.desafiosicredi.service;

import br.com.gabrielfelicidade.desafiosicredi.dto.CreateVoteRequest;
import br.com.gabrielfelicidade.desafiosicredi.entity.VoteEntity;
import br.com.gabrielfelicidade.desafiosicredi.exception.ScheduleDoesNotHaveOpenVotingSession;
import br.com.gabrielfelicidade.desafiosicredi.exception.UserAlreadyVotedInScheduleException;
import br.com.gabrielfelicidade.desafiosicredi.mapper.VoteMapper;
import br.com.gabrielfelicidade.desafiosicredi.repository.VoteRepository;
import br.com.gabrielfelicidade.desafiosicredi.service.impl.VoteServiceImpl;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @InjectMocks
    private VoteServiceImpl voteService;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private VoteMapper voteMapper;

    @Mock
    private VotingSessionService votingSessionService;

    private EasyRandom random = new EasyRandom();

    @Test
    void shouldCreateVote() {
        final var userId = random.nextObject(UUID.class).toString();
        final var request = random.nextObject(CreateVoteRequest.class);
        final var entity = random.nextObject(VoteEntity.class);

        when(votingSessionService.scheduleDoesHaveOpenVotingSession(request.getScheduleId())).thenReturn(true);
        when(voteRepository.existsByUserIdAndScheduleId(userId, request.getScheduleId())).thenReturn(false);
        when(voteMapper.toEntity(userId, request)).thenReturn(entity);

        voteService.createVote(userId, request);

        verify(voteRepository).save(entity);
    }

    @Test
    void shouldNotCreateVoteWhenDoesNotHaveOpenVotingSession() {
        final var userId = random.nextObject(UUID.class).toString();
        final var request = random.nextObject(CreateVoteRequest.class);
        final var entity = random.nextObject(VoteEntity.class);

        when(votingSessionService.scheduleDoesHaveOpenVotingSession(request.getScheduleId())).thenReturn(false);

        assertThrows(ScheduleDoesNotHaveOpenVotingSession.class, () -> voteService.createVote(userId, request));

        verify(voteRepository, never()).save(entity);
    }

    @Test
    void shouldNotCreateVoteWhenUserAlreadyVotedInSchedule() {
        final var userId = random.nextObject(UUID.class).toString();
        final var request = random.nextObject(CreateVoteRequest.class);
        final var entity = random.nextObject(VoteEntity.class);

        when(votingSessionService.scheduleDoesHaveOpenVotingSession(request.getScheduleId())).thenReturn(true);
        when(voteRepository.existsByUserIdAndScheduleId(userId, request.getScheduleId())).thenReturn(true);

        assertThrows(UserAlreadyVotedInScheduleException.class, () -> voteService.createVote(userId, request));

        verify(voteRepository, never()).save(entity);
    }
}