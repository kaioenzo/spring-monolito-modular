package kaioenzo.contabancaria.conta;

import kaioenzo.contabancaria.common.CommomMapper;
import kaioenzo.contabancaria.common.exceptions.EntidadeNaoEncontrada;
import kaioenzo.contabancaria.common.interfaces.ServiceIF;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaBancariaService implements ServiceIF<ContaBancaria, CriaContaBancariaDTO> {

    private final ContaBancariaRepository contaBancariaRepository;

    @Override
    public ContaBancaria criar(CriaContaBancariaDTO criaContaBancariaDTO) {
        return contaBancariaRepository.save(new ContaBancaria());
    }

    public ContaBancaria buscar(String id) {
        UUID uuid = CommomMapper.converterUUID(id);
        return contaBancariaRepository.findById(uuid).orElseThrow(
                () -> new EntidadeNaoEncontrada(ContaBancariaExceptionsMessage.CONTA_NAO_ENCONTRADA)
        );
    }

    public BigDecimal buscarSaldo(String id) {
        return buscar(id).calcularSaldo();
    }
}
