package kaioenzo.contabancaria.transacao;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String message) {
        super(message);
    }

    public SaldoInsuficienteException() {
        super("Saldo insuficiente.");
    }
}
