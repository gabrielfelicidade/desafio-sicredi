package br.com.gabrielfelicidade.desafiosicredi.producer;

import br.com.gabrielfelicidade.desafiosicredi.entity.VotingSessionEntity;
import br.com.gabrielfelicidade.desafiosicredi.exception.SendKafkaMessageException;
import br.com.gabrielfelicidade.desafiosicredi.mapper.VotingSessionMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VotingSessionFinishedProducer {

    @Value("${topic.voting-session-finished}")
    private String topicName;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final VotingSessionMapper votingSessionMapper;

    public void send(final VotingSessionEntity votingSession) {
        try {
            final var event = votingSessionMapper.toEvent(votingSession);
            final var stringEvent = objectMapper.writeValueAsString(event);

            log.info("Enviando mensagem de fim de sessão: {}", stringEvent);

            kafkaTemplate.send(topicName, stringEvent);
        } catch (JsonProcessingException e) {
            throw new SendKafkaMessageException(e);
        }
    }
}