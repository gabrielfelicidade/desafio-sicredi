package br.com.gabrielfelicidade.desafiosicredi.listener;

import br.com.gabrielfelicidade.desafiosicredi.dto.VotingSessionFinishedEvent;
import br.com.gabrielfelicidade.desafiosicredi.exception.ReadKafkaMessageException;
import br.com.gabrielfelicidade.desafiosicredi.service.ScheduleService;
import br.com.gabrielfelicidade.desafiosicredi.service.VotingSessionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class VotingSessionFinishedListener {

    private final VotingSessionService votingSessionService;
    private final ScheduleService scheduleService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${topic.voting-session-finished}")
    public void consume(final ConsumerRecord<String, String> payload) {
        log.info("Consuming message: {}", payload.value());

        try {
            final var event = objectMapper.readValue(payload.value(), VotingSessionFinishedEvent.class);
            final var votingSession = votingSessionService.findById(event.getId());

            scheduleService.updateScheduleStatus(votingSession.getSchedule().getId());
        } catch (JsonProcessingException e) {
            throw new ReadKafkaMessageException(e);
        }
    }
}