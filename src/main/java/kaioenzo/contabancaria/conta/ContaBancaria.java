package kaioenzo.contabancaria.conta;

import jakarta.persistence.*;
import kaioenzo.contabancaria.transacao.TipoTransacao;
import kaioenzo.contabancaria.transacao.Transacao;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
public class ContaBancaria {
    public ContaBancaria(Set<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @OneToMany(mappedBy = "contaBancaria")
    private Set<Transacao> transacoes = new HashSet<>();

    public BigDecimal calcularSaldo() {
        return transacoes.stream()
                .map(t -> t.getTipoTransacao() == TipoTransacao.DEPOSITO ?
                        t.getValor() :
                        t.getValor().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
