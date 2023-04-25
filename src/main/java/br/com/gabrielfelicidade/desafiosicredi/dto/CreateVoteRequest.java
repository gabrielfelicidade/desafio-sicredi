package br.com.gabrielfelicidade.desafiosicredi.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateVoteRequest {

    private UUID scheduleId;

    private boolean accepted;
}