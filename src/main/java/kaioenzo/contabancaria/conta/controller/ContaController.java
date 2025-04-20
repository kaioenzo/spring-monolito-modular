package kaioenzo.contabancaria.conta.controller;

import kaioenzo.contabancaria.conta.ContaBancariaDTO;
import kaioenzo.contabancaria.conta.ContaBancariaService;
import kaioenzo.contabancaria.conta.controller.schemas.CriaContaBancariaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conta")
@RequiredArgsConstructor
public class ContaController {
    private final ContaBancariaService service;

    @PostMapping
    public ResponseEntity<ContaBancariaDTO> criar(CriaContaBancariaDTO criaContaBancariaDTO) {
        return ResponseEntity.ok().body(service.criar(criaContaBancariaDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaBancariaDTO> buscar(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(service.buscar(id));
    }
}
