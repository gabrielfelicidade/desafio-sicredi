package br.com.gabrielfelicidade.desafiosicredi.dto;

import lombok.Data;

import java.time.Duration;
import java.util.UUID;

@Data
public class CreateVotingSessionRequest {

    private UUID scheduleId;

    private Duration duration;
}