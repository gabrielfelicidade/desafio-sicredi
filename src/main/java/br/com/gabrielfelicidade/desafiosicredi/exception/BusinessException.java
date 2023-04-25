package br.com.gabrielfelicidade.desafiosicredi.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(final String message,
                             final Exception rootCause) {
        super(message, rootCause);
    }
}
