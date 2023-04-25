package br.com.gabrielfelicidade.desafiosicredi.repository;

import br.com.gabrielfelicidade.desafiosicredi.dto.VotingSessionStatus;
import br.com.gabrielfelicidade.desafiosicredi.entity.VotingSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface VotingSessionRepository extends JpaRepository<VotingSessionEntity, UUID> {

    List<VotingSessionEntity> findByEndDateLessThanAndStatusEquals(final LocalDateTime endDate,
                                                                   final VotingSessionStatus status);

    boolean existsByScheduleIdEquals(final UUID scheduleId);

    boolean existsByScheduleIdAndStatus(final UUID scheduleId, final VotingSessionStatus status);

    @Modifying
    @Query("UPDATE VotingSessionEntity vs SET vs.status = br.com.gabrielfelicidade.desafiosicredi.dto.VotingSessionStatus.FINISHED WHERE vs.id = :votingSessionId")
    void finishVotingSession(@Param("votingSessionId") final UUID votingSessionId);
}