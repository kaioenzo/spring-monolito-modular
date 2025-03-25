package kaioenzo.contabancaria.transacao;

import kaioenzo.contabancaria.common.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class SaldoInsuficienteException extends BaseException {
    public SaldoInsuficienteException() {
        super("Saldo insuficiente.", HttpStatus.PRECONDITION_FAILED.value());
    }
}
