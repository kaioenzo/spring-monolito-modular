package kaioenzo.contabancaria.operacao_bancaria.service;

import kaioenzo.contabancaria.common.CommomMapper;
import kaioenzo.contabancaria.common.SaldoInsuficienteException;
import kaioenzo.contabancaria.conta.ContaBancariaDTO;
import kaioenzo.contabancaria.conta.ContaBancariaService;
import kaioenzo.contabancaria.operacao_bancaria.controller.schemas.CriaSaqueDTO;
import kaioenzo.contabancaria.saldo.SaldoService;
import kaioenzo.contabancaria.transacao.TipoTransacao;
import kaioenzo.contabancaria.transacao.TransacaoDTO;
import kaioenzo.contabancaria.transacao.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class SaqueService implements OperacaoBancaria<CriaSaqueDTO> {
    private final ContaBancariaService contaBancariaService;
    private final SaldoService saldoService;
    private final TransacaoService transacaoService;

    @Override
    public TipoTransacao getTipoTransacao() {
        return TipoTransacao.SAQUE;
    }

    @Override
    public TransacaoDTO realizarOperacao(CriaSaqueDTO dto) {
        verificarSaque(dto);
        return transacaoService.criar(CommomMapper.converterUUID(dto.conta()), dto.valor(), getTipoTransacao());
    }

    private void verificarSaque(CriaSaqueDTO dto) {
        ContaBancariaDTO contaBancaria = contaBancariaService.buscar(dto.conta());
        BigDecimal saldo = saldoService.calcularSaldo(contaBancaria.getId().toString());
        BigDecimal valorSaque = BigDecimal.valueOf(dto.valor());

        if (saldo.subtract(valorSaque).compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoInsuficienteException();
        }

    }
}
