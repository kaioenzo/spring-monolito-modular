package kaioenzo.contabancaria.transacao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import kaioenzo.contabancaria.conta.ContaBancaria;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false, updatable = false)
    @JsonBackReference
    private ContaBancaria contaBancaria;

    @Column(updatable = false, nullable = false)
    private BigDecimal valor;

    @Column(updatable = false, nullable = false)
    private TipoTransacao tipoTransacao;

    @Column(updatable = false, nullable = false)
    private LocalDateTime dataHora;

    public Transacao(ContaBancaria conta, BigDecimal valor, TipoTransacao tipoTransacao, LocalDateTime dataHora) {
        this.contaBancaria = conta;
        this.valor = valor;
        this.tipoTransacao = tipoTransacao;
        this.dataHora = dataHora;
    }
}
