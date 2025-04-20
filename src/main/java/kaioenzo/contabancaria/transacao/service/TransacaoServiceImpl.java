package kaioenzo.contabancaria.transacao.service;

import kaioenzo.contabancaria.common.CommomMapper;
import kaioenzo.contabancaria.common.EntidadeNaoEncontradaException;
import kaioenzo.contabancaria.transacao.TipoTransacao;
import kaioenzo.contabancaria.transacao.TransacaoDTO;
import kaioenzo.contabancaria.transacao.TransacaoService;
import kaioenzo.contabancaria.transacao.domain.Transacao;
import kaioenzo.contabancaria.transacao.domain.TransacaoExceptionsMessage;
import kaioenzo.contabancaria.transacao.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransacaoServiceImpl implements TransacaoService {
    private final TransacaoRepository transacaoRepository;

    @Override
    public TransacaoDTO criar(UUID contaId, Double valor, TipoTransacao tipoTransacao) {
        Transacao transacao = new Transacao(contaId, BigDecimal.valueOf(valor), tipoTransacao, LocalDateTime.now());
        transacao = transacaoRepository.save(transacao);
        return new TransacaoDTO(transacao.getId(), contaId, transacao.getValor(), tipoTransacao, transacao.getDataTransacao());
    }

    @Override
    public TransacaoDTO buscar(String id) {
        UUID uuid = CommomMapper.converterUUID(id);
        Transacao transacao = transacaoRepository.findById(uuid).orElseThrow(
                () -> new EntidadeNaoEncontradaException(TransacaoExceptionsMessage.TRANSACAO_NAO_ENCONTRADA)
        );
        return new TransacaoDTO(transacao.getId(), transacao.getContaId(), transacao.getValor(), transacao.getTipoTransacao(), transacao.getDataTransacao());
    }

    @Override
    public List<TransacaoDTO> listar(UUID contaId) {
        List<Transacao> transacoes = transacaoRepository.findAllByContaBancaria_Id(contaId);
        return transacoes
                .stream()
                .map(t -> new TransacaoDTO(t.getId(), t.getContaId(), t.getValor(), t.getTipoTransacao(), t.getDataTransacao()))
                .collect(Collectors.toList());
    }

}
