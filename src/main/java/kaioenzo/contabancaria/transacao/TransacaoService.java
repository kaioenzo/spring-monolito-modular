package kaioenzo.contabancaria.transacao;

import kaioenzo.contabancaria.common.interfaces.ServiceIF;
import kaioenzo.contabancaria.conta.ContaBancaria;
import kaioenzo.contabancaria.conta.ContaBancariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransacaoService implements ServiceIF<Transacao, CriaTransacaoDTO> {
    private final ContaBancariaService contaBancariaService;
    private final TransacaoRepository transacaoRepository;

    @Override
    public Transacao criar(CriaTransacaoDTO criaTransacaoDTO) {
        ContaBancaria contaBancaria = contaBancariaService.buscar(criaTransacaoDTO.contaId());
        Transacao transacao = new Transacao(contaBancaria, BigDecimal.valueOf(criaTransacaoDTO.valor()), criaTransacaoDTO.tipoTransacao(), LocalDateTime.now());
        return transacaoRepository.save(transacao);
    }

    @Override
    public Transacao buscar(String id) {
        return null;
    }
}
