package kaioenzo.contabancaria.transacao;

import java.util.List;
import java.util.UUID;

public interface TransacaoService {
    TransacaoDTO criar(UUID contaId, Double valor, TipoTransacao tipoTransacao);

    TransacaoDTO buscar(String id);

    List<TransacaoDTO> listar(UUID contaId);
}
