package br.com.gabrielfelicidade.desafiosicredi.exception;

public class ScheduleNotFoundException extends BusinessException {

    public ScheduleNotFoundException() {
        super("A pauta não pôde ser encontrada", null);
    }
}
