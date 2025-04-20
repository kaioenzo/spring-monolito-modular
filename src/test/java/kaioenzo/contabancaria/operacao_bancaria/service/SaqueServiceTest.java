package kaioenzo.contabancaria.operacao_bancaria.service;

import kaioenzo.contabancaria.common.CommomMapper;
import kaioenzo.contabancaria.common.SaldoInsuficienteException;
import kaioenzo.contabancaria.conta.ContaBancariaDTO;
import kaioenzo.contabancaria.conta.ContaBancariaService;
import kaioenzo.contabancaria.operacao_bancaria.controller.schemas.CriaSaqueDTO;
import kaioenzo.contabancaria.saldo.SaldoService;
import kaioenzo.contabancaria.transacao.TipoTransacao;
import kaioenzo.contabancaria.transacao.TransacaoDTO;
import kaioenzo.contabancaria.transacao.TransacaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaqueServiceTest {

    @Mock
    private ContaBancariaService contaBancariaService;

    @Mock
    private SaldoService saldoService;

    @Mock
    private TransacaoService transacaoService;

    @InjectMocks
    private SaqueService saqueService;

    @Test
    void deveTerTipoTransacaoSaque() {
        // Act
        TipoTransacao tipoTransacao = saqueService.getTipoTransacao();

        // Assert
        assertEquals(TipoTransacao.SAQUE, tipoTransacao);
    }

    @Test
    void deveRealizarSaqueComSucesso() {
        // Arrange
        String contaId = "123e4567-e89b-12d3-a456-426614174000";
        UUID contaUUID = UUID.fromString(contaId);
        Double valorSaque = 50.0;
        CriaSaqueDTO dto = new CriaSaqueDTO(contaId, valorSaque);

        ContaBancariaDTO contaBancariaDTO = new ContaBancariaDTO(contaUUID);
        TransacaoDTO transacaoDTO = new TransacaoDTO(
                UUID.randomUUID(),
                contaUUID,
                BigDecimal.valueOf(valorSaque),
                TipoTransacao.SAQUE,
                LocalDateTime.now()
        );

        try (MockedStatic<CommomMapper> mockedStatic = Mockito.mockStatic(CommomMapper.class)) {
            mockedStatic.when(() -> CommomMapper.converterUUID(contaId)).thenReturn(contaUUID);

            when(contaBancariaService.buscar(contaId)).thenReturn(contaBancariaDTO);
            when(saldoService.calcularSaldo(contaId)).thenReturn(BigDecimal.valueOf(100.0)); // Saldo maior que o valor do saque
            when(transacaoService.criar(contaUUID, valorSaque, TipoTransacao.SAQUE)).thenReturn(transacaoDTO);

            // Act
            TransacaoDTO resultado = saqueService.realizarOperacao(dto);

            // Assert
            assertEquals(transacaoDTO, resultado);

            verify(contaBancariaService, times(1)).buscar(contaId);
            verify(saldoService, times(1)).calcularSaldo(contaId);
            verify(transacaoService, times(1)).criar(contaUUID, valorSaque, TipoTransacao.SAQUE);
            mockedStatic.verify(() -> CommomMapper.converterUUID(contaId), times(1));
        }
    }

    @Test
    void deveLancarExcecaoQuandoSaldoInsuficiente() {
        // Arrange
        String contaId = "123e4567-e89b-12d3-a456-426614174000";
        UUID contaUUID = UUID.fromString(contaId);
        Double valorSaque = 150.0; // Valor maior que o saldo
        CriaSaqueDTO dto = new CriaSaqueDTO(contaId, valorSaque);

        ContaBancariaDTO contaBancariaDTO = new ContaBancariaDTO(contaUUID);

        when(contaBancariaService.buscar(contaId)).thenReturn(contaBancariaDTO);
        when(saldoService.calcularSaldo(contaId)).thenReturn(BigDecimal.valueOf(100.0)); // Saldo menor que o valor do saque

        // Act & Assert
        assertThrows(SaldoInsuficienteException.class, () -> saqueService.realizarOperacao(dto));

        verify(contaBancariaService, times(1)).buscar(contaId);
        verify(saldoService, times(1)).calcularSaldo(contaId);
        verify(transacaoService, never()).criar(any(), any(), any());
    }

    @Test
    void deveRealizarSaqueQuandoSaldoIgualAoValor() {
        // Arrange
        String contaId = "123e4567-e89b-12d3-a456-426614174000";
        UUID contaUUID = UUID.fromString(contaId);
        Double valorSaque = 100.0; // Valor igual ao saldo
        CriaSaqueDTO dto = new CriaSaqueDTO(contaId, valorSaque);

        ContaBancariaDTO contaBancariaDTO = new ContaBancariaDTO(contaUUID);
        TransacaoDTO transacaoDTO = new TransacaoDTO(
                UUID.randomUUID(),
                contaUUID,
                BigDecimal.valueOf(valorSaque),
                TipoTransacao.SAQUE,
                LocalDateTime.now()
        );

        try (MockedStatic<CommomMapper> mockedStatic = Mockito.mockStatic(CommomMapper.class)) {
            mockedStatic.when(() -> CommomMapper.converterUUID(contaId)).thenReturn(contaUUID);

            when(contaBancariaService.buscar(contaId)).thenReturn(contaBancariaDTO);
            when(saldoService.calcularSaldo(contaId)).thenReturn(BigDecimal.valueOf(100.0)); // Saldo igual ao valor do saque
            when(transacaoService.criar(contaUUID, valorSaque, TipoTransacao.SAQUE)).thenReturn(transacaoDTO);

            // Act
            TransacaoDTO resultado = saqueService.realizarOperacao(dto);

            // Assert
            assertEquals(transacaoDTO, resultado);

            verify(contaBancariaService, times(1)).buscar(contaId);
            verify(saldoService, times(1)).calcularSaldo(contaId);
            verify(transacaoService, times(1)).criar(contaUUID, valorSaque, TipoTransacao.SAQUE);
            mockedStatic.verify(() -> CommomMapper.converterUUID(contaId), times(1));
        }
    }
}
