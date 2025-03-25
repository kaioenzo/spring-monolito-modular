package kaioenzo.contabancaria.conta;

import jakarta.persistence.*;
import kaioenzo.contabancaria.transacao.Transacao;
import lombok.Getter;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Entity
public class ContaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @OneToMany(mappedBy = "contaBancaria")
    private Set<Transacao> transacoes;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContaBancaria that = (ContaBancaria) o;
        return Objects.equals(id, that.id) && Objects.equals(transacoes, that.transacoes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
