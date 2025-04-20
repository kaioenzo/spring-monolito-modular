package kaioenzo.contabancaria.conta;

import kaioenzo.contabancaria.conta.controller.schemas.CriaContaBancariaDTO;

public interface ContaBancariaService {
    ContaBancariaDTO buscar(String id);

    ContaBancariaDTO criar(CriaContaBancariaDTO dto);

}
