package br.com.gabrielfelicidade.desafiosicredi.service;

import br.com.gabrielfelicidade.desafiosicredi.dto.CreateVotingSessionRequest;
import br.com.gabrielfelicidade.desafiosicredi.dto.VotingSession;
import br.com.gabrielfelicidade.desafiosicredi.entity.VotingSessionEntity;

import java.util.List;
import java.util.UUID;

public interface VotingSessionService {

    VotingSession createVotingSession(final CreateVotingSessionRequest request);

    List<VotingSessionEntity> findVotingSessionsToFinish();

    void finishPendingVotingSessions();

    boolean scheduleDoesHaveOpenVotingSession(final UUID scheduleId);

    VotingSessionEntity findById(final UUID id);
}