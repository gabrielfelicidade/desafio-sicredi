package br.com.gabrielfelicidade.desafiosicredi.exception;

public class SendKafkaMessageException extends BusinessException {

    public SendKafkaMessageException(final Exception e) {
        super("Erro ao enviar mensagem via kafka", e);
    }
}