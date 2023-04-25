package br.com.gabrielfelicidade.desafiosicredi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Entity
@Getter
@NoArgsConstructor
@Table(name = "vote")
@Setter
public class VoteEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @Column(columnDefinition = "uuid")
    private UUID id;

    private boolean accepted;

    private String userId;

    @ManyToOne
    private ScheduleEntity schedule;
}