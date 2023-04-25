package br.com.gabrielfelicidade.desafiosicredi.exception;

public class UserAlreadyVotedInScheduleException extends BusinessException {

    public UserAlreadyVotedInScheduleException() {
        super("O usuário já votou nessa pauta", null);
    }
}