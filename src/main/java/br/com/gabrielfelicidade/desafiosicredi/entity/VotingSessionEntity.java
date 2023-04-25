package br.com.gabrielfelicidade.desafiosicredi.entity;

import br.com.gabrielfelicidade.desafiosicredi.dto.VotingSessionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Entity
@Getter
@NoArgsConstructor
@Table(name = "vote_session")
@Setter
public class VotingSessionEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @Column(columnDefinition = "uuid")
    private UUID id;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private VotingSessionStatus status;

    @OneToOne
    private ScheduleEntity schedule;
}