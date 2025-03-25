package kaioenzo.contabancaria.conta;

import jakarta.persistence.*;
import kaioenzo.contabancaria.transacao.Transacao;
import lombok.Getter;

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
}
