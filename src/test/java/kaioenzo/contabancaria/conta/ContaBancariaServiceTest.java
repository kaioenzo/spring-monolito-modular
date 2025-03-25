package kaioenzo.contabancaria.conta;

import kaioenzo.contabancaria.common.exceptions.EntidadeNaoEncontrada;
import kaioenzo.contabancaria.transacao.TipoTransacao;
import kaioenzo.contabancaria.transacao.Transacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContaBancariaServiceTest {

    @Mock
    private ContaBancariaRepository repository;
    @InjectMocks
    private ContaBancariaService service;

    @Test
    @DisplayName("deveria ter saldo zerado")
    void deveriaTerSaldoZerado() {
        ContaBancaria contaBancaria = new ContaBancaria();
        BigDecimal saldo = BigDecimal.ZERO;
        assertEquals(saldo, contaBancaria.calcularSaldo());
    }

    @Test
    @DisplayName("deveria calcular saldo com transacoes")
    void deveriaCalcularSaldoComTransacoes() {
        Transacao deposito = new Transacao(null, new BigDecimal("500.00"), TipoTransacao.DEPOSITO, null);
        Transacao saque = new Transacao(null, new BigDecimal("200.00"), TipoTransacao.SAQUE, null);

        Set<Transacao> transacoes = Set.of(deposito, saque);
        ContaBancaria contaBancaria = new ContaBancaria(transacoes);

        BigDecimal saldoEsperado = new BigDecimal("300.00");
        assertEquals(saldoEsperado, contaBancaria.calcularSaldo());
    }

    @Test
    @DisplayName("deveria buscar conta com sucesso")
    void deveriaBuscarConta_comSucesso() {
        UUID uuid = UUID.randomUUID();
        ContaBancaria contaBancaria = new ContaBancaria();
        when(repository.findById(uuid)).thenReturn(Optional.of(contaBancaria));

        ContaBancaria conta = service.buscar(uuid.toString());

        assertNotNull(conta);
        assertEquals(contaBancaria, conta);
    }

    @Test
    @DisplayName("deveria buscar conta e lançar exception caso não exista")
    void deveriaBuscarConta_eLancarExceptionCasoNaoExista() {
        UUID uuid = UUID.randomUUID();
        when(repository.findById(uuid)).thenReturn(Optional.empty());

        EntidadeNaoEncontrada exception = assertThrows(EntidadeNaoEncontrada.class, () -> service.buscar(uuid.toString()));
        assertEquals(ContaBancariaExceptionsMessage.CONTA_NAO_ENCONTRADA, exception.getMessage());
    }

    @Test
    @DisplayName("deveria criar conta com sucesso")
    void deveriaCriarContaComSucesso() {
        ContaBancaria contaBancaria = new ContaBancaria();
        when(repository.save(any(ContaBancaria.class))).thenReturn(contaBancaria);

        ContaBancaria contaCriada = service.criar(new CriaContaBancariaDTO());

        assertNotNull(contaCriada);
        assertEquals(contaBancaria, contaCriada);
    }

    @Test
    @DisplayName("deveria falhar ao criar conta se erro no repository")
    void deveriaFalharAoCriarContaSeErroNoRepository() {
        when(repository.save(any(ContaBancaria.class))).thenThrow(new RuntimeException("Erro ao salvar"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> service.criar(new CriaContaBancariaDTO()));

        assertEquals("Erro ao salvar", exception.getMessage());
    }
}