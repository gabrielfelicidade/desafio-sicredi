package br.com.gabrielfelicidade.desafiosicredi.exception;

public class ScheduleHadVotingSessionException extends BusinessException {

    public ScheduleHadVotingSessionException() {
        super("A pauta está sendo/já foi votada", null);
    }
}