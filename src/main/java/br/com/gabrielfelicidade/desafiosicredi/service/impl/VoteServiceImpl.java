package br.com.gabrielfelicidade.desafiosicredi.service.impl;

import br.com.gabrielfelicidade.desafiosicredi.dto.CreateVoteRequest;
import br.com.gabrielfelicidade.desafiosicredi.dto.Vote;
import br.com.gabrielfelicidade.desafiosicredi.exception.ScheduleDoesNotHaveOpenVotingSession;
import br.com.gabrielfelicidade.desafiosicredi.exception.UserAlreadyVotedInScheduleException;
import br.com.gabrielfelicidade.desafiosicredi.mapper.VoteMapper;
import br.com.gabrielfelicidade.desafiosicredi.repository.VoteRepository;
import br.com.gabrielfelicidade.desafiosicredi.service.VoteService;
import br.com.gabrielfelicidade.desafiosicredi.service.VotingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;
    private final VotingSessionService votingSessionService;

    @Override
    public Vote createVote(final String userId,
                           final CreateVoteRequest request) {
        if (scheduleDoesNotHaveOpenVotingSession(request.getScheduleId())) {
            throw new ScheduleDoesNotHaveOpenVotingSession();
        }

        if (userAlreadyVotedInSchedule(userId, request.getScheduleId())) {
            throw new UserAlreadyVotedInScheduleException();
        }

        final var voteEntity = voteRepository.save(voteMapper.toEntity(userId, request));

        return voteMapper.toVote(voteEntity);
    }

    @Override
    public long getScheduleAcceptanceVotes(final UUID scheduleId) {
        return voteRepository.countByScheduleIdAndAcceptedTrue(scheduleId);
    }

    @Override
    public long getScheduleRejectionVotes(final UUID scheduleId) {
        return voteRepository.countByScheduleIdAndAcceptedFalse(scheduleId);
    }

    private boolean userAlreadyVotedInSchedule(final String userId,
                                               final UUID scheduleId) {
        return voteRepository.existsByUserIdAndScheduleId(userId, scheduleId);
    }

    private boolean scheduleDoesNotHaveOpenVotingSession(final UUID scheduleId) {
        return !votingSessionService.scheduleDoesHaveOpenVotingSession(scheduleId);
    }
}