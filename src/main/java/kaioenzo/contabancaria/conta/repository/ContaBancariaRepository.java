package kaioenzo.contabancaria.conta.repository;

import kaioenzo.contabancaria.conta.domain.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, UUID> {
}
