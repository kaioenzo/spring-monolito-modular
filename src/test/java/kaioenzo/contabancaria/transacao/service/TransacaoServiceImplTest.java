package kaioenzo.contabancaria.transacao.service;

import kaioenzo.contabancaria.common.CommomMapper;
import kaioenzo.contabancaria.common.EntidadeNaoEncontradaException;
import kaioenzo.contabancaria.transacao.TipoTransacao;
import kaioenzo.contabancaria.transacao.TransacaoDTO;
import kaioenzo.contabancaria.transacao.domain.Transacao;
import kaioenzo.contabancaria.transacao.repository.TransacaoRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceImplTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @InjectMocks
    private TransacaoServiceImpl transacaoService;

    @Test
    void deveCriarTransacaoComSucesso() {
        // Arrange
        UUID contaId = UUID.randomUUID();
        Double valor = 100.0;
        TipoTransacao tipoTransacao = TipoTransacao.DEPOSITO;
        LocalDateTime dataTransacao = LocalDateTime.now();

        Transacao transacaoSalva = new Transacao(contaId, BigDecimal.valueOf(valor), tipoTransacao, dataTransacao);
        transacaoSalva.setId(UUID.randomUUID());

        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacaoSalva);

        // Act
        TransacaoDTO resultado = transacaoService.criar(contaId, valor, tipoTransacao);

        // Assert
        assertNotNull(resultado);
        assertEquals(transacaoSalva.getId(), resultado.id());
        assertEquals(contaId, resultado.contaId());
        assertEquals(BigDecimal.valueOf(valor), resultado.valor());
        assertEquals(tipoTransacao, resultado.tipoTransacao());

        verify(transacaoRepository, times(1)).save(any(Transacao.class));
    }

    @Test
    void deveBuscarTransacaoComSucesso() {
        // Arrange
        String idTransacao = "123e4567-e89b-12d3-a456-426614174000";
        UUID uuid = UUID.fromString(idTransacao);
        UUID contaId = UUID.randomUUID();
        BigDecimal valor = BigDecimal.valueOf(200.0);
        TipoTransacao tipoTransacao = TipoTransacao.SAQUE;
        LocalDateTime dataTransacao = LocalDateTime.now();

        Transacao transacao = new Transacao(contaId, valor, tipoTransacao, dataTransacao);
        transacao.setId(uuid);

        try (MockedStatic<CommomMapper> mockedStatic = Mockito.mockStatic(CommomMapper.class)) {
            mockedStatic.when(() -> CommomMapper.converterUUID(idTransacao)).thenReturn(uuid);

            when(transacaoRepository.findById(uuid)).thenReturn(Optional.of(transacao));

            // Act
            TransacaoDTO resultado = transacaoService.buscar(idTransacao);

            // Assert
            assertNotNull(resultado);
            assertEquals(uuid, resultado.id());
            assertEquals(contaId, resultado.contaId());
            assertEquals(valor, resultado.valor());
            assertEquals(tipoTransacao, resultado.tipoTransacao());
            assertEquals(dataTransacao, resultado.dataTransacao());

            verify(transacaoRepository, times(1)).findById(uuid);
            mockedStatic.verify(() -> CommomMapper.converterUUID(idTransacao), times(1));
        }
    }

    @Test
    void deveLancarExcecaoQuandoTransacaoNaoEncontrada() {
        // Arrange
        String idTransacao = "123e4567-e89b-12d3-a456-426614174000";
        UUID uuid = UUID.fromString(idTransacao);

        try (MockedStatic<CommomMapper> mockedStatic = Mockito.mockStatic(CommomMapper.class)) {
            mockedStatic.when(() -> CommomMapper.converterUUID(idTransacao)).thenReturn(uuid);

            when(transacaoRepository.findById(uuid)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(EntidadeNaoEncontradaException.class, () -> transacaoService.buscar(idTransacao));

            verify(transacaoRepository, times(1)).findById(uuid);
            mockedStatic.verify(() -> CommomMapper.converterUUID(idTransacao), times(1));
        }
    }

    @Test
    void deveListarTransacoesDeUmaConta() {
        // Arrange
        UUID contaId = UUID.randomUUID();

        Transacao transacao1 = new Transacao(contaId, BigDecimal.valueOf(100.0), TipoTransacao.DEPOSITO, LocalDateTime.now());
        transacao1.setId(UUID.randomUUID());

        Transacao transacao2 = new Transacao(contaId, BigDecimal.valueOf(50.0), TipoTransacao.SAQUE, LocalDateTime.now());
        transacao2.setId(UUID.randomUUID());

        List<Transacao> transacoes = Arrays.asList(transacao1, transacao2);

        when(transacaoRepository.findAllByContaBancaria_Id(contaId)).thenReturn(transacoes);

        // Act
        List<TransacaoDTO> resultado = transacaoService.listar(contaId);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        assertEquals(transacao1.getId(), resultado.get(0).id());
        assertEquals(contaId, resultado.get(0).contaId());
        assertEquals(transacao1.getValor(), resultado.get(0).valor());
        assertEquals(transacao1.getTipoTransacao(), resultado.get(0).tipoTransacao());

        assertEquals(transacao2.getId(), resultado.get(1).id());
        assertEquals(contaId, resultado.get(1).contaId());
        assertEquals(transacao2.getValor(), resultado.get(1).valor());
        assertEquals(transacao2.getTipoTransacao(), resultado.get(1).tipoTransacao());

        verify(transacaoRepository, times(1)).findAllByContaBancaria_Id(contaId);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaTransacoes() {
        // Arrange
        UUID contaId = UUID.randomUUID();

        when(transacaoRepository.findAllByContaBancaria_Id(contaId)).thenReturn(List.of());

        // Act
        List<TransacaoDTO> resultado = transacaoService.listar(contaId);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(transacaoRepository, times(1)).findAllByContaBancaria_Id(contaId);
    }
}
