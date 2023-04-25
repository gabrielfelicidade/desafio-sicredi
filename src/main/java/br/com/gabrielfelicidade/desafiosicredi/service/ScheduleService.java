package br.com.gabrielfelicidade.desafiosicredi.service;

import br.com.gabrielfelicidade.desafiosicredi.dto.CreateScheduleRequest;
import br.com.gabrielfelicidade.desafiosicredi.dto.Schedule;

import java.util.UUID;

public interface ScheduleService {

    Schedule createSchedule(final CreateScheduleRequest request);

    void updateScheduleStatus(final UUID scheduleId);
}