package kaioenzo.contabancaria.conta;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/conta")
@RequiredArgsConstructor
public class ContaController {
    private final ContaBancariaService contaBancariaService;

    @GetMapping("/saldo")
    public ResponseEntity<BigDecimal> buscarSaldo(@RequestParam("id") String id) {
        return ResponseEntity.ok(contaBancariaService.buscarSaldo(id));
    }
}
