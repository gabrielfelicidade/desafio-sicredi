package br.com.gabrielfelicidade.desafiosicredi.producer;

import br.com.gabrielfelicidade.desafiosicredi.dto.VotingSessionFinishedEvent;
import br.com.gabrielfelicidade.desafiosicredi.entity.VotingSessionEntity;
import br.com.gabrielfelicidade.desafiosicredi.mapper.VotingSessionMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VotingSessionFinishedProducerTest {


    @InjectMocks
    private VotingSessionFinishedProducer producer;

    @Mock
    private ObjectMapper objectMapperMock;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private VotingSessionMapper votingSessionMapper;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private EasyRandom random = new EasyRandom();

    @Test
    void shouldSendMessageSuccessfully() throws JsonProcessingException {
        final var entity = random.nextObject(VotingSessionEntity.class);
        final var event = random.nextObject(VotingSessionFinishedEvent.class);
        final var eventString = objectMapper.writeValueAsString(event);

        when(votingSessionMapper.toEvent(entity)).thenReturn(event);
        when(objectMapperMock.writeValueAsString(event)).thenReturn(eventString);

        producer.send(entity);

        verify(kafkaTemplate).send(null, eventString);
    }
}