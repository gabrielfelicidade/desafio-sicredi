package br.com.gabrielfelicidade.desafiosicredi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class VotingSession {

    private UUID id;

    private LocalDateTime endDate;

    private VotingSessionStatus status;

    private UUID scheduleId;
}