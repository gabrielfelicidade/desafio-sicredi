package br.com.gabrielfelicidade.desafiosicredi.controller;

import br.com.gabrielfelicidade.desafiosicredi.dto.CreateVoteRequest;
import br.com.gabrielfelicidade.desafiosicredi.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> createVote(@RequestHeader("user_id") final String userId,
                                           @RequestBody final CreateVoteRequest request) {
        final var vote = voteService.createVote(userId, request);
        final var uri = URI.create(String.format("/votes/%s", vote.getId()));

        return ResponseEntity.created(uri).build();
    }
}
