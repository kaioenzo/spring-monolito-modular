package kaioenzo.contabancaria.transacao.controller.schemas;

import kaioenzo.contabancaria.transacao.TipoTransacao;

public record CriaTransacaoDTO(String contaId, TipoTransacao tipoTransacao, Double valor) {
}
