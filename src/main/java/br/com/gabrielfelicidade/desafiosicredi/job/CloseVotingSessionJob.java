package br.com.gabrielfelicidade.desafiosicredi.job;

import br.com.gabrielfelicidade.desafiosicredi.service.VotingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CloseVotingSessionJob {

    private final VotingSessionService votingSessionService;

    @Scheduled(fixedDelay = 1000)
    public void closeVotingSession() {
        votingSessionService.finishPendingVotingSessions();
    }
}
