package kaioenzo.contabancaria.transacao;

import kaioenzo.contabancaria.common.exceptions.EntidadeNaoEncontrada;
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
        CriaTransacaoDTO dto = new CriaTransacaoDTO(contaId.toString(), TipoTransacao.SAQUE, 100.0);
        ContaBancaria contaBancaria = new ContaBancaria();
        when(contaBancariaService.buscar(contaId.toString())).thenReturn(contaBancaria);

        assertThrows(SaldoInsuficienteException.class, () -> service.criar(dto));


    }

    @Test
    @DisplayName("deveria buscar transacao com sucesso")
    void deveriaBuscarTransacaoComSucesso() {
        UUID transacaoId = UUID.randomUUID();
        Transacao transacao = new Transacao();
        when(transacaoRepository.findById(transacaoId)).thenReturn(java.util.Optional.of(transacao));

        Transacao resultado = service.buscar(transacaoId.toString());

        Assertions.assertNotNull(resultado);
        Assertions.assertEquals(transacao, resultado);
    }

    @Test
    @DisplayName("deveria lançar EntidadeNaoEncontradaException quando transacao nao for encontrada")
    void deveriaLancarEntidadeNaoEncontradaException() {
        UUID transacaoId = UUID.randomUUID();
        when(transacaoRepository.findById(transacaoId)).thenReturn(java.util.Optional.empty());

        assertThrows(EntidadeNaoEncontrada.class, () -> service.buscar(transacaoId.toString()));
    }
}
