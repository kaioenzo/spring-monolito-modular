package kaioenzo.contabancaria.transacao.domain;

import jakarta.persistence.*;
import kaioenzo.contabancaria.transacao.TipoTransacao;
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

    @Column(nullable = false)
    private UUID contaId;

    @Column(updatable = false, nullable = false)
    private BigDecimal valor;

    @Column(updatable = false, nullable = false)
    private TipoTransacao tipoTransacao;

    @Column(updatable = false, nullable = false)
    private LocalDateTime dataTransacao;

    public Transacao(UUID contaId, BigDecimal valor, TipoTransacao tipoTransacao, LocalDateTime dataTransacao) {
        this.contaId = contaId;
        this.valor = valor;
        this.tipoTransacao = tipoTransacao;
        this.dataTransacao = dataTransacao;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
