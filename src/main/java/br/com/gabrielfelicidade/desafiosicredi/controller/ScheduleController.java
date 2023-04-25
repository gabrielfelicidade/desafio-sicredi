package br.com.gabrielfelicidade.desafiosicredi.controller;

import br.com.gabrielfelicidade.desafiosicredi.dto.CreateScheduleRequest;
import br.com.gabrielfelicidade.desafiosicredi.dto.Schedule;
import br.com.gabrielfelicidade.desafiosicredi.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody final CreateScheduleRequest request) {
        final var schedule = scheduleService.createSchedule(request);
        final var uri = URI.create(String.format("/schedules/%s", schedule.getId()));

        return ResponseEntity.created(uri).body(schedule);
    }
}
