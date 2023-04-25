package br.com.gabrielfelicidade.desafiosicredi.exception;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException() {
        super("Não foi possível encontrar a entidade", null);
    }
}