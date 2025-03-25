package kaioenzo.contabancaria.conta;

import kaioenzo.contabancaria.common.interfaces.ControllerIF;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/conta")
@RequiredArgsConstructor
public class ContaController implements ControllerIF<ContaBancaria, CriaContaBancariaDTO> {
    private final ContaBancariaService contaBancariaService;

    @GetMapping("/saldo")
    public ResponseEntity<BigDecimal> buscarSaldo(@RequestParam("id") String id) {
        return ResponseEntity.ok(contaBancariaService.buscarSaldo(id));
    }

    @PostMapping
    @Override
    public ResponseEntity<ContaBancaria> criar(CriaContaBancariaDTO criaContaBancariaDTO) {
        return ResponseEntity.ok().body(contaBancariaService.criar(criaContaBancariaDTO));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ContaBancaria> buscar(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(contaBancariaService.buscar(id));
    }
}
