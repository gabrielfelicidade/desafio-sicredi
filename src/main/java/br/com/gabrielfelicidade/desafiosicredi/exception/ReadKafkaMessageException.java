package br.com.gabrielfelicidade.desafiosicredi.exception;

public class ReadKafkaMessageException extends BusinessException {

    public ReadKafkaMessageException(final Exception e) {
        super("Erro ao ler mensagem via kafka", e);
    }
}