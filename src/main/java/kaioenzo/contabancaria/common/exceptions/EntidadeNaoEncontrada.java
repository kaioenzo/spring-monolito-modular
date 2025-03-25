package kaioenzo.contabancaria.common.exceptions;

import org.springframework.http.HttpStatus;

public class EntidadeNaoEncontrada extends BaseException {
    public EntidadeNaoEncontrada(String message) {
        super(message, HttpStatus.NOT_FOUND.value());
    }
}
