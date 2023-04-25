package br.com.gabrielfelicidade.desafiosicredi.controller;

import br.com.gabrielfelicidade.desafiosicredi.dto.CreateVotingSessionRequest;
import br.com.gabrielfelicidade.desafiosicredi.dto.VotingSession;
import br.com.gabrielfelicidade.desafiosicredi.service.VotingSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/voting-sessions")
@RequiredArgsConstructor
public class VotingSessionController {

    private final VotingSessionService votingSessionService;

    @PostMapping
    public ResponseEntity<VotingSession> createVotingSession(@RequestBody final CreateVotingSessionRequest request) {
        final var persistedVotingSession = votingSessionService.createVotingSession(request);
        final var uri = URI.create(String.format("/schedules/%s", persistedVotingSession.getId()));

        return ResponseEntity.created(uri).body(persistedVotingSession);
    }
}
