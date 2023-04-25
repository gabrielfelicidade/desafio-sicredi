package br.com.gabrielfelicidade.desafiosicredi.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {

    private final String message;
}
