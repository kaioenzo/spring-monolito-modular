package kaioenzo.contabancaria.common;

import org.springframework.http.HttpStatus;

public class SaldoInsuficienteException extends BaseException {
    public SaldoInsuficienteException() {
        super("Saldo insuficiente.", HttpStatus.PRECONDITION_FAILED.value());
    }
}
