package br.com.gabrielfelicidade.desafiosicredi.service;

import br.com.gabrielfelicidade.desafiosicredi.dto.CreateVoteRequest;
import br.com.gabrielfelicidade.desafiosicredi.dto.Vote;

import java.util.UUID;

public interface VoteService {

    Vote createVote(final String userId, final CreateVoteRequest request);

    long getScheduleAcceptanceVotes(final UUID scheduleId);

    long getScheduleRejectionVotes(final UUID scheduleId);
}