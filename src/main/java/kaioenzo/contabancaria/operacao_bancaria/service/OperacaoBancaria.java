package kaioenzo.contabancaria.operacao_bancaria.service;

import kaioenzo.contabancaria.transacao.TipoTransacao;
import kaioenzo.contabancaria.transacao.TransacaoDTO;

public interface OperacaoBancaria<T> {
    TipoTransacao getTipoTransacao();

    TransacaoDTO realizarOperacao(T dto);
}
