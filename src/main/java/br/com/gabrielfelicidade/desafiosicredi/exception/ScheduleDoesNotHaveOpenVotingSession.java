package br.com.gabrielfelicidade.desafiosicredi.exception;

public class ScheduleDoesNotHaveOpenVotingSession extends BusinessException {

    public ScheduleDoesNotHaveOpenVotingSession() {
        super("A pauta não tem nenhuma votação em aberto", null);
    }
}