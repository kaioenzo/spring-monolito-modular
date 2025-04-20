package kaioenzo.contabancaria.operacao_bancaria.controller.schemas;

public record CriarDepositoDTO(
        String contaId, Double valor
) {
}
