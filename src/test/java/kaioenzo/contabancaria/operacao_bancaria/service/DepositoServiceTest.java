package kaioenzo.contabancaria.operacao_bancaria.service;

import kaioenzo.contabancaria.conta.ContaBancariaDTO;
import kaioenzo.contabancaria.conta.ContaBancariaService;
import kaioenzo.contabancaria.operacao_bancaria.controller.schemas.CriarDepositoDTO;
import kaioenzo.contabancaria.transacao.TipoTransacao;
import kaioenzo.contabancaria.transacao.TransacaoDTO;
import kaioenzo.contabancaria.transacao.TransacaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepositoServiceTest {

    @Mock
    private ContaBancariaService contaBancariaService;

    @Mock
    private TransacaoService transacaoService;

    @InjectMocks
    private DepositoService depositoService;

    @Test
    void deveTerTipoTransacaoDeposito() {
        // Act
        TipoTransacao tipoTransacao = depositoService.getTipoTransacao();

        // Assert
        assertEquals(TipoTransacao.DEPOSITO, tipoTransacao);
    }

    @Test
    void deveRealizarDepositoComSucesso() {
        // Arrange
        String contaId = "123e4567-e89b-12d3-a456-426614174000";
        UUID contaUUID = UUID.fromString(contaId);
        Double valorDeposito = 100.0;

        CriarDepositoDTO dto = new CriarDepositoDTO(contaId, valorDeposito);
        ContaBancariaDTO contaBancariaDTO = new ContaBancariaDTO(contaUUID);

        TransacaoDTO transacaoDTO = new TransacaoDTO(
                UUID.randomUUID(),
                contaUUID,
                BigDecimal.valueOf(valorDeposito),
                TipoTransacao.DEPOSITO,
                LocalDateTime.now()
        );

        when(contaBancariaService.buscar(contaId)).thenReturn(contaBancariaDTO);
        when(transacaoService.criar(contaUUID, valorDeposito, TipoTransacao.DEPOSITO)).thenReturn(transacaoDTO);

        // Act
        TransacaoDTO resultado = depositoService.realizarOperacao(dto);

        // Assert
        assertEquals(transacaoDTO, resultado);

        verify(contaBancariaService, times(1)).buscar(contaId);
        verify(transacaoService, times(1)).criar(contaUUID, valorDeposito, TipoTransacao.DEPOSITO);
    }

    @Test
    void deveRealizarDepositoComValorMinimo() {
        // Arrange
        String contaId = "123e4567-e89b-12d3-a456-426614174000";
        UUID contaUUID = UUID.fromString(contaId);
        Double valorDeposito = 0.01; // Valor mínimo possível

        CriarDepositoDTO dto = new CriarDepositoDTO(contaId, valorDeposito);
        ContaBancariaDTO contaBancariaDTO = new ContaBancariaDTO(contaUUID);

        TransacaoDTO transacaoDTO = new TransacaoDTO(
                UUID.randomUUID(),
                contaUUID,
                BigDecimal.valueOf(valorDeposito),
                TipoTransacao.DEPOSITO,
                LocalDateTime.now()
        );

        when(contaBancariaService.buscar(contaId)).thenReturn(contaBancariaDTO);
        when(transacaoService.criar(contaUUID, valorDeposito, TipoTransacao.DEPOSITO)).thenReturn(transacaoDTO);

        // Act
        TransacaoDTO resultado = depositoService.realizarOperacao(dto);

        // Assert
        assertEquals(transacaoDTO, resultado);

        verify(contaBancariaService, times(1)).buscar(contaId);
        verify(transacaoService, times(1)).criar(contaUUID, valorDeposito, TipoTransacao.DEPOSITO);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -1.0, -100.0})
    void deveLancarExcecaoQuandoValorDepositoInvalido(Double valorInvalido) {
        // Arrange
        String contaId = "123e4567-e89b-12d3-a456-426614174000";
        CriarDepositoDTO dto = new CriarDepositoDTO(contaId, valorInvalido);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> depositoService.realizarOperacao(dto));

        assertEquals("Valor deve ser maior que zero", exception.getMessage());

        verify(contaBancariaService, never()).buscar(any());
        verify(transacaoService, never()).criar(any(), any(), any());
    }
}
