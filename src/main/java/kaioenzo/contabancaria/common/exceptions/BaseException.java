package kaioenzo.contabancaria.common.exceptions;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final int status;

    public BaseException(String message, int status) {
        super(message);
        this.status = status;
    }
}
