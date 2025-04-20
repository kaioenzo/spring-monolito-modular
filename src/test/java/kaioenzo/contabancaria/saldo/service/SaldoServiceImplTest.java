package kaioenzo.contabancaria.saldo.service;

import kaioenzo.contabancaria.common.CommomMapper;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaldoServiceImplTest {

    @Mock
    private TransacaoService transacaoService;

    @InjectMocks
    private SaldoServiceImpl saldoService;

    @Test
    void deveCalcularSaldoComApenasDepositos() {
        // Arrange
        String contaId = "123e4567-e89b-12d3-a456-426614174000";
        UUID contaUUID = UUID.fromString(contaId);

        try (MockedStatic<CommomMapper> mockedStatic = Mockito.mockStatic(CommomMapper.class)) {
            mockedStatic.when(() -> CommomMapper.converterUUID(contaId)).thenReturn(contaUUID);

            List<TransacaoDTO> transacoes = Arrays.asList(
                    new TransacaoDTO(UUID.randomUUID(), contaUUID, new BigDecimal("50.00"), TipoTransacao.DEPOSITO, LocalDateTime.now()),
                    new TransacaoDTO(UUID.randomUUID(), contaUUID, new BigDecimal("75.50"), TipoTransacao.DEPOSITO, LocalDateTime.now())
            );

            when(transacaoService.listar(contaUUID)).thenReturn(transacoes);

            // Act
            BigDecimal resultado = saldoService.calcularSaldo(contaId);

            // Assert
            assertEquals(new BigDecimal("125.50"), resultado);
            verify(transacaoService, times(1)).listar(contaUUID);
            mockedStatic.verify(() -> CommomMapper.converterUUID(contaId), times(1));
        }
    }

    @Test
    void deveCalcularSaldoComDepositosESaques() {
        // Arrange
        String contaId = "123e4567-e89b-12d3-a456-426614174000";
        UUID contaUUID = UUID.fromString(contaId);

        try (MockedStatic<CommomMapper> mockedStatic = Mockito.mockStatic(CommomMapper.class)) {
            mockedStatic.when(() -> CommomMapper.converterUUID(contaId)).thenReturn(contaUUID);

            List<TransacaoDTO> transacoes = Arrays.asList(
                    new TransacaoDTO(UUID.randomUUID(), contaUUID, new BigDecimal("100.00"), TipoTransacao.DEPOSITO, LocalDateTime.now()),
                    new TransacaoDTO(UUID.randomUUID(), contaUUID, new BigDecimal("50.00"), TipoTransacao.SAQUE, LocalDateTime.now()),
                    new TransacaoDTO(UUID.randomUUID(), contaUUID, new BigDecimal("25.00"), TipoTransacao.DEPOSITO, LocalDateTime.now())
            );

            when(transacaoService.listar(contaUUID)).thenReturn(transacoes);

            // Act
            BigDecimal resultado = saldoService.calcularSaldo(contaId);

            // Assert
            assertEquals(new BigDecimal("75.00"), resultado);
            verify(transacaoService, times(1)).listar(contaUUID);
            mockedStatic.verify(() -> CommomMapper.converterUUID(contaId), times(1));
        }
    }

    @Test
    void deveRetornarZeroQuandoNaoHaTransacoes() {
        // Arrange
        String contaId = "123e4567-e89b-12d3-a456-426614174000";
        UUID contaUUID = UUID.fromString(contaId);

        try (MockedStatic<CommomMapper> mockedStatic = Mockito.mockStatic(CommomMapper.class)) {
            mockedStatic.when(() -> CommomMapper.converterUUID(contaId)).thenReturn(contaUUID);

            when(transacaoService.listar(contaUUID)).thenReturn(Collections.emptyList());

            // Act
            BigDecimal resultado = saldoService.calcularSaldo(contaId);

            // Assert
            assertEquals(BigDecimal.ZERO, resultado);
            verify(transacaoService, times(1)).listar(contaUUID);
            mockedStatic.verify(() -> CommomMapper.converterUUID(contaId), times(1));
        }
    }

    @Test
    void deveCalcularSaldoNegativoQuandoSaquesMaioresQueDepositos() {
        // Arrange
        String contaId = "123e4567-e89b-12d3-a456-426614174000";
        UUID contaUUID = UUID.fromString(contaId);

        try (MockedStatic<CommomMapper> mockedStatic = Mockito.mockStatic(CommomMapper.class)) {
            mockedStatic.when(() -> CommomMapper.converterUUID(contaId)).thenReturn(contaUUID);

            List<TransacaoDTO> transacoes = Arrays.asList(
                    new TransacaoDTO(UUID.randomUUID(), contaUUID, new BigDecimal("50.00"), TipoTransacao.DEPOSITO, LocalDateTime.now()),
                    new TransacaoDTO(UUID.randomUUID(), contaUUID, new BigDecimal("100.00"), TipoTransacao.SAQUE, LocalDateTime.now())
            );

            when(transacaoService.listar(contaUUID)).thenReturn(transacoes);

            // Act
            BigDecimal resultado = saldoService.calcularSaldo(contaId);

            // Assert
            assertEquals(new BigDecimal("-50.00"), resultado);
            verify(transacaoService, times(1)).listar(contaUUID);
            mockedStatic.verify(() -> CommomMapper.converterUUID(contaId), times(1));
        }
    }

    @Test
    void deveLidarComUUIDInvalido() {
        // Arrange
        String contaIdInvalido = "id-invalido";

        try (MockedStatic<CommomMapper> mockedStatic = Mockito.mockStatic(CommomMapper.class)) {
            mockedStatic.when(() -> CommomMapper.converterUUID(contaIdInvalido))
                    .thenThrow(new IllegalArgumentException("UUID inválido"));

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> saldoService.calcularSaldo(contaIdInvalido));
            mockedStatic.verify(() -> CommomMapper.converterUUID(contaIdInvalido), times(1));
            verify(transacaoService, never()).listar(any());
        }
    }
}
