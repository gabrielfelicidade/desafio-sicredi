package br.com.gabrielfelicidade.desafiosicredi.entity;

import br.com.gabrielfelicidade.desafiosicredi.dto.ScheduleStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "schedule")
@With
public class ScheduleEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String description;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;
}