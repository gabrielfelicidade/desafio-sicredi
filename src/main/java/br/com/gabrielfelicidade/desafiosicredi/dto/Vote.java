package br.com.gabrielfelicidade.desafiosicredi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class Vote {

    private UUID id;

    private boolean accepted;

    private String userId;

    private UUID scheduleId;
}