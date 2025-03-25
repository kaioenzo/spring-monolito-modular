package kaioenzo.contabancaria.conta;

import kaioenzo.contabancaria.common.exceptions.EntidadeNaoEncontrada;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContaBancariaServiceTest {

    @Mock
    private ContaBancariaRepository repository;
    @InjectMocks
    private ContaBancariaService service;

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
}