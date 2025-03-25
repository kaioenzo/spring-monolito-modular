package kaioenzo.contabancaria.transacao;

import kaioenzo.contabancaria.conta.ContaBancaria;
import kaioenzo.contabancaria.conta.ContaBancariaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

    @InjectMocks
    private TransacaoService service;
    @Mock
    private ContaBancariaService contaBancariaService;
    @Mock
    private TransacaoRepository transacaoRepository;

    @Test
    @DisplayName("deveria criar transacao com sucesso")
    void deveriaCriarTransacaoComSucesso() {
        UUID contaId = UUID.randomUUID();
        CriaTransacaoDTO dto = new CriaTransacaoDTO(contaId.toString(), TipoTransacao.DEPOSITO, 100.0);
        ContaBancaria contaBancaria = new ContaBancaria();
        Transacao transacaoCriada = new Transacao();
        when(contaBancariaService.buscar(contaId.toString())).thenReturn(contaBancaria);
        when(transacaoRepository.save(any())).thenReturn(transacaoCriada);

        Transacao transacao = service.criar(dto);

        Assertions.assertNotNull(transacao);
    }

    @Test
    @DisplayName("deveria lançar exception para transação de depósito com saldo insuficiente")
    void deveriaLancarExceptionParaTransacaoDeDepositoComSaldoInsuficiente() {
        UUID contaId = UUID.randomUUID();
        CriaTransacaoDTO dto = new CriaTransacaoDTO(contaId.toString(), TipoTransacao.DEPOSITO, 100.0);
        ContaBancaria contaBancaria = new ContaBancaria();
        Transacao transacaoCriada = new Transacao();
        when(contaBancariaService.buscar(contaId.toString())).thenReturn(contaBancaria);

        assertThrows(SaldoInsuficienteException.class, () -> service.criar(dto));


    }
}
