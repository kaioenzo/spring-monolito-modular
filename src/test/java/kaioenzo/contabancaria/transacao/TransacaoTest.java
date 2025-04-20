package kaioenzo.contabancaria.transacao;

import kaioenzo.contabancaria.conta.repository.ContaBancariaRepository;
import kaioenzo.contabancaria.transacao.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class TransacaoTest {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

//    @Test
//    @DisplayName("deveria criar entidade transacao corretamente com construtor com todos os argumentos")
//    void deveriaCriarEntidadeTransacaoCorretamenteComTodosOsArgumentos() {
//        Transacao transacao = criarTransacao();
//
//        assertNotNull(transacao);
//        assertNotNull(transacao.getContaId());
//        assertEquals(new BigDecimal(10), transacao.getValor());
//        assertEquals(TipoTransacao.DEPOSITO, transacao.getTipoTransacao());
//        assertNotNull(transacao.getDataTransacao());
//    }
//
//    @Test
//    @DisplayName("deveria salvar transacao e gerar uuid")
//    void deveriaSalvarTransacaoEGerarUuid() {
//        Transacao transacao = criarTransacao();
//
//        Transacao transacaoSalva = transacaoRepository.save(transacao);
//
//        assertNotNull(transacaoSalva);
//        assertNotNull(transacaoSalva.getId());
//        assertInstanceOf(UUID.class, transacaoSalva.getId());
//    }


//    private Transacao criarTransacao() {
//        ContaBancaria contaBancaria = new ContaBancaria();
//        contaBancaria = contaBancariaRepository.save(contaBancaria);
//
//        return new Transacao(contaBancaria, new BigDecimal(10), TipoTransacao.DEPOSITO, LocalDateTime.now());
//    }
}
