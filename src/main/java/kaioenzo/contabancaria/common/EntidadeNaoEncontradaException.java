package kaioenzo.contabancaria.common;

import org.springframework.http.HttpStatus;

public class EntidadeNaoEncontradaException extends BaseException {
    public EntidadeNaoEncontradaException(String message) {
        super(message, HttpStatus.NOT_FOUND.value());
    }
}
