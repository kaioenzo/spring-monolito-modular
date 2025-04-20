package kaioenzo.contabancaria.operacao_bancaria.service;

import kaioenzo.contabancaria.conta.ContaBancariaDTO;
import kaioenzo.contabancaria.conta.ContaBancariaService;
import kaioenzo.contabancaria.operacao_bancaria.controller.schemas.CriarDepositoDTO;
import kaioenzo.contabancaria.transacao.TipoTransacao;
import kaioenzo.contabancaria.transacao.TransacaoDTO;
import kaioenzo.contabancaria.transacao.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepositoService implements OperacaoBancaria<CriarDepositoDTO> {
    private final ContaBancariaService contaBancariaService;
    private final TransacaoService transacaoService;

    @Override
    public TipoTransacao getTipoTransacao() {
        return TipoTransacao.DEPOSITO;
    }

    @Override
    public TransacaoDTO realizarOperacao(CriarDepositoDTO dto) {
        validaValorDeposito(dto.valor());

        ContaBancariaDTO conta = contaBancariaService.buscar(dto.contaId());

        return transacaoService.criar(
                conta.getId(),
                dto.valor(),
                getTipoTransacao()
        );
    }

    private void validaValorDeposito(Double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }
    }
}
