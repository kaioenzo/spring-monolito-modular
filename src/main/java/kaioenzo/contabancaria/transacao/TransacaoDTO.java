package kaioenzo.contabancaria.transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransacaoDTO(UUID id, UUID contaId, BigDecimal valor, TipoTransacao tipoTransacao,
                           LocalDateTime dataTransacao) {
}
