package kaioenzo.contabancaria.transacao;

import kaioenzo.contabancaria.conta.ContaBancaria;
import kaioenzo.contabancaria.conta.ContaBancariaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TransacaoTest {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @Test
    @DisplayName("deveria criar entidade transacao corretamente com construtor com todos os argumentos")
    void deveriaCriarEntidadeTransacaoCorretamenteComTodosOsArgumentos() {
        Transacao transacao = criarTransacao();

        assertNotNull(transacao);
        assertNotNull(transacao.getContaBancaria());
        assertEquals(new BigDecimal(10), transacao.getValor());
        assertEquals(TipoTransacao.DEPOSITO, transacao.getTipoTransacao());
        assertNotNull(transacao.getDataHora());
    }

    @Test
    @DisplayName("deveria salvar transacao e gerar uuid")
    void deveriaSalvarTransacaoEGerarUuid() {
        Transacao transacao = criarTransacao();

        Transacao transacaoSalva = transacaoRepository.save(transacao);

        assertNotNull(transacaoSalva);
        assertNotNull(transacaoSalva.getId());
        assertInstanceOf(UUID.class, transacaoSalva.getId());
    }


    private Transacao criarTransacao() {
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria = contaBancariaRepository.save(contaBancaria);

        return new Transacao(contaBancaria, new BigDecimal(10), TipoTransacao.DEPOSITO, LocalDateTime.now());
    }
}
