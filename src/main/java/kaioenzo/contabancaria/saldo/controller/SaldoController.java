package kaioenzo.contabancaria.saldo.controller;

import kaioenzo.contabancaria.saldo.service.SaldoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequestMapping("/saldo")
@RestController
@RequiredArgsConstructor
public class SaldoController {
    private final SaldoServiceImpl saldoService;

    @GetMapping("/{contaId}")
    public BigDecimal saldo(@PathVariable String contaId) {
        return saldoService.calcularSaldo(contaId);
    }
}
