package kaioenzo.contabancaria.transacao;

import kaioenzo.contabancaria.common.CommomMapper;
import kaioenzo.contabancaria.common.exceptions.EntidadeNaoEncontrada;
import kaioenzo.contabancaria.common.interfaces.ServiceIF;
import kaioenzo.contabancaria.conta.ContaBancaria;
import kaioenzo.contabancaria.conta.ContaBancariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransacaoService implements ServiceIF<Transacao, CriaTransacaoDTO> {
    private final ContaBancariaService contaBancariaService;
    private final TransacaoRepository transacaoRepository;

    @Override
    public Transacao criar(CriaTransacaoDTO criaTransacaoDTO) {
        ContaBancaria contaBancaria = contaBancariaService.buscar(criaTransacaoDTO.contaId());
        if (criaTransacaoDTO.tipoTransacao() == TipoTransacao.SAQUE) {
            validarDeposito(contaBancaria, BigDecimal.valueOf(criaTransacaoDTO.valor()));
        }
        Transacao transacao = new Transacao(contaBancaria, BigDecimal.valueOf(criaTransacaoDTO.valor()), criaTransacaoDTO.tipoTransacao(), LocalDateTime.now());
        return transacaoRepository.save(transacao);
    }

    private void validarDeposito(ContaBancaria contaBancaria, BigDecimal valor) {
        if (contaBancaria.calcularSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException();
        }
    }

    @Override
    public Transacao buscar(String id) {
        UUID uuid = CommomMapper.converterUUID(id);
        return transacaoRepository.findById(uuid).orElseThrow(
                () -> new EntidadeNaoEncontrada(TransacaoExceptionsMessage.TRANSACAO_NAO_ENCONTRADA)
        );
    }
}
