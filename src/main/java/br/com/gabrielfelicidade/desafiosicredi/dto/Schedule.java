package br.com.gabrielfelicidade.desafiosicredi.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class Schedule {

    private UUID id;

    private String description;

    private ScheduleStatus status;
}