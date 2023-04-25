package br.com.gabrielfelicidade.desafiosicredi.repository;

import br.com.gabrielfelicidade.desafiosicredi.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoteRepository extends JpaRepository<VoteEntity, UUID> {

    boolean existsByUserIdAndScheduleId(final String userId, final UUID scheduleId);

    long countByScheduleIdAndAcceptedTrue(final UUID scheduleId);

    long countByScheduleIdAndAcceptedFalse(final UUID scheduleId);
}