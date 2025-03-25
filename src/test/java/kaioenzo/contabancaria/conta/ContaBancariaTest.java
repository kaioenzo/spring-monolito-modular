package kaioenzo.contabancaria.conta;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class ContaBancariaTest {
    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @Test
    @DisplayName("deveria criar classe com todos os argumentos")
    void deveriaCriarClasseComTodosOsArgumentos() {
        ContaBancaria contaBancaria = new ContaBancaria();
        assertNotNull(contaBancaria);
    }

    @Test
    @DisplayName("deveria criar classe sem argumentos")
    void deveriaCriarClasseSemArgumentos() {
        ContaBancaria contaBancaria = new ContaBancaria();
        assertNotNull(contaBancaria);
    }

    @Test
    @DisplayName("deveria salvar nova conta bancaria")
    void deveriaSalvarNovaConta() {
        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria = contaBancariaRepository.save(contaBancaria);

        assertNotNull(contaBancaria);
        assertNotNull(contaBancaria.getId());
    }

}