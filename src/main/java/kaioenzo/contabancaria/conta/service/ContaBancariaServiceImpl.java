package kaioenzo.contabancaria.conta.service;

import kaioenzo.contabancaria.common.CommomMapper;
import kaioenzo.contabancaria.common.EntidadeNaoEncontradaException;
import kaioenzo.contabancaria.conta.ContaBancariaDTO;
import kaioenzo.contabancaria.conta.ContaBancariaService;
import kaioenzo.contabancaria.conta.controller.schemas.CriaContaBancariaDTO;
import kaioenzo.contabancaria.conta.domain.ContaBancaria;
import kaioenzo.contabancaria.conta.domain.ContaBancariaExceptionsMessage;
import kaioenzo.contabancaria.conta.repository.ContaBancariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaBancariaServiceImpl implements ContaBancariaService {

    private final ContaBancariaRepository contaBancariaRepository;

    public ContaBancariaDTO criar(CriaContaBancariaDTO criaContaBancariaDTO) {
        ContaBancaria conta = contaBancariaRepository.save(new ContaBancaria());
        return new ContaBancariaDTO(conta.getId());
    }

    public ContaBancariaDTO buscar(String id) {
        UUID uuid = CommomMapper.converterUUID(id);
        ContaBancaria conta = contaBancariaRepository.findById(uuid).orElseThrow(
                () -> new EntidadeNaoEncontradaException(ContaBancariaExceptionsMessage.CONTA_NAO_ENCONTRADA)
        );
        return new ContaBancariaDTO(conta.getId());
    }
}
