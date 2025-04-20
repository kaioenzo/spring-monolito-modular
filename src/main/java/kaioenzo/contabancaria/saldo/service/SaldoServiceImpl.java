package kaioenzo.contabancaria.saldo.service;

import kaioenzo.contabancaria.common.CommomMapper;
import kaioenzo.contabancaria.saldo.SaldoService;
import kaioenzo.contabancaria.transacao.TipoTransacao;
import kaioenzo.contabancaria.transacao.TransacaoDTO;
import kaioenzo.contabancaria.transacao.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaldoServiceImpl implements SaldoService {
    private final TransacaoService transacaoService;

    @Override
    public BigDecimal calcularSaldo(String contaId) {
        List<TransacaoDTO> transacoes = transacaoService.listar(CommomMapper.converterUUID(contaId));

        return transacoes.stream()
                .map(t -> t.tipoTransacao() == TipoTransacao.DEPOSITO ?
                        t.valor() :
                        t.valor().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}

