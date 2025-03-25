package kaioenzo.contabancaria.transacao;

public record CriaTransacaoDTO(String contaId, TipoTransacao tipoTransacao, Double valor) {
}
