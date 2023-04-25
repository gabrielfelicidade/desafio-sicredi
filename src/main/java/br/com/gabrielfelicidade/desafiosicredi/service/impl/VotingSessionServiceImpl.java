package br.com.gabrielfelicidade.desafiosicredi.service.impl;

import br.com.gabrielfelicidade.desafiosicredi.dto.CreateVotingSessionRequest;
import br.com.gabrielfelicidade.desafiosicredi.dto.VotingSession;
import br.com.gabrielfelicidade.desafiosicredi.dto.VotingSessionStatus;
import br.com.gabrielfelicidade.desafiosicredi.entity.VotingSessionEntity;
import br.com.gabrielfelicidade.desafiosicredi.exception.EntityNotFoundException;
import br.com.gabrielfelicidade.desafiosicredi.exception.ScheduleHadVotingSessionException;
import br.com.gabrielfelicidade.desafiosicredi.exception.ScheduleNotFoundException;
import br.com.gabrielfelicidade.desafiosicredi.mapper.VotingSessionMapper;
import br.com.gabrielfelicidade.desafiosicredi.producer.VotingSessionFinishedProducer;
import br.com.gabrielfelicidade.desafiosicredi.repository.ScheduleRepository;
import br.com.gabrielfelicidade.desafiosicredi.repository.VotingSessionRepository;
import br.com.gabrielfelicidade.desafiosicredi.service.VotingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class VotingSessionServiceImpl implements VotingSessionService {

    private static final Duration DEFAULT_VOTING_SESSION_DURATION = Duration.ofMinutes(1);
    private final VotingSessionRepository votingSessionRepository;
    private final VotingSessionMapper votingSessionMapper;
    private final VotingSessionFinishedProducer votingSessionFinishedProducer;
    private final ScheduleRepository scheduleRepository;

    @Override
    public VotingSession createVotingSession(final CreateVotingSessionRequest request) {
        if (scheduleDoesNotExists(request)) {
            throw new ScheduleNotFoundException();
        }

        if (scheduleHadVotingSession(request)) {
            throw new ScheduleHadVotingSessionException();
        }

        final var duration = Objects.requireNonNullElse(request.getDuration(), DEFAULT_VOTING_SESSION_DURATION);
        final var votingSession =
                votingSessionRepository.save(votingSessionMapper.toEntity(request.getScheduleId(), duration));

        return votingSessionMapper.toVotingSession(votingSession);
    }

    @Override
    public List<VotingSessionEntity> findVotingSessionsToFinish() {
        final var endDate = LocalDateTime.now();
        final var status = VotingSessionStatus.STARTED;

        return votingSessionRepository.findByEndDateLessThanAndStatusEquals(endDate, status);
    }

    @Override
    @Transactional
    public void finishPendingVotingSessions() {
        final var votingSessions = findVotingSessionsToFinish();

        votingSessions.parallelStream()
                .forEach(this::finishVotingSession);

        votingSessions.parallelStream()
                .forEach(votingSessionFinishedProducer::send);
    }

    @Override
    public boolean scheduleDoesHaveOpenVotingSession(final UUID scheduleId) {
        return votingSessionRepository.existsByScheduleIdAndStatus(scheduleId, VotingSessionStatus.STARTED);
    }

    @Override
    public VotingSessionEntity findById(final UUID id) {
        return votingSessionRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    private boolean scheduleHadVotingSession(final CreateVotingSessionRequest request) {
        return votingSessionRepository.existsByScheduleIdEquals(request.getScheduleId());
    }

    private void finishVotingSession(final VotingSessionEntity votingSession) {
        votingSessionRepository.finishVotingSession(votingSession.getId());
    }

    private boolean scheduleDoesNotExists(final CreateVotingSessionRequest request) {
        return !scheduleRepository.existsById(request.getScheduleId());
    }
}
