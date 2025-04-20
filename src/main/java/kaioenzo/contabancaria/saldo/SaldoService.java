package kaioenzo.contabancaria.saldo;

import java.math.BigDecimal;

public interface SaldoService {
    BigDecimal calcularSaldo(String contaId);
}
