package kaioenzo.contabancaria.common.exceptions;

import org.springframework.http.HttpStatus;

public class UUIDInvalidoException extends BaseException {
    public UUIDInvalidoException() {
        super("Id inválido.", HttpStatus.BAD_REQUEST.value());
    }
}
