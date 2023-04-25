package br.com.gabrielfelicidade.desafiosicredi.mapper;

import br.com.gabrielfelicidade.desafiosicredi.dto.CreateVoteRequest;
import br.com.gabrielfelicidade.desafiosicredi.dto.Vote;
import br.com.gabrielfelicidade.desafiosicredi.entity.ScheduleEntity;
import br.com.gabrielfelicidade.desafiosicredi.entity.VoteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    default VoteEntity toEntity(final String userId,
                                final CreateVoteRequest request) {
        return VoteEntity.builder()
                .accepted(request.isAccepted())
                .userId(userId)
                .schedule(ScheduleEntity.builder().id(request.getScheduleId()).build())
                .build();
    }

    default Vote toVote(final VoteEntity voteEntity) {
        return Vote.builder()
                .id(voteEntity.getId())
                .scheduleId(voteEntity.getSchedule().getId())
                .accepted(voteEntity.isAccepted())
                .userId(voteEntity.getUserId())
                .build();
    }
}
