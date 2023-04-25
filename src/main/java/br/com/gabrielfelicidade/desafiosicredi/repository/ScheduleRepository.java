package br.com.gabrielfelicidade.desafiosicredi.repository;

import br.com.gabrielfelicidade.desafiosicredi.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, UUID> {
}