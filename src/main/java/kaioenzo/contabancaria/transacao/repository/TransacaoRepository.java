package kaioenzo.contabancaria.transacao.repository;

import kaioenzo.contabancaria.transacao.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
    @Query(
            """ 
                        select t from Transacao t where t.contaId = :contaId
                    """
    )
    List<Transacao> findAllByContaBancaria_Id(UUID contaId);
}
