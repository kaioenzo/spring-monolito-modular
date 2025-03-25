package kaioenzo.contabancaria.conta;

import kaioenzo.contabancaria.common.exceptions.EntidadeNaoEncontrada;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaBancariaService {

    private final ContaBancariaRepository contaBancariaRepository;

    public ContaBancaria buscar(String id) {
        return contaBancariaRepository.findById(UUID.fromString(id)).orElseThrow(
                () -> new EntidadeNaoEncontrada(ContaBancariaExceptionsMessage.CONTA_NAO_ENCONTRADA)
        );
    }

    public BigDecimal buscarSaldo(String id) {
        return buscar(id).calcularSaldo();
    }
}
