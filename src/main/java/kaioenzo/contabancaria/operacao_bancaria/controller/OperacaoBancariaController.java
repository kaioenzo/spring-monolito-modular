package kaioenzo.contabancaria.operacao_bancaria.controller;

import kaioenzo.contabancaria.operacao_bancaria.controller.schemas.CriaSaqueDTO;
import kaioenzo.contabancaria.operacao_bancaria.controller.schemas.CriarDepositoDTO;
import kaioenzo.contabancaria.operacao_bancaria.service.DepositoService;
import kaioenzo.contabancaria.operacao_bancaria.service.SaqueService;
import kaioenzo.contabancaria.transacao.TransacaoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operacao-bancaria")
@RequiredArgsConstructor
public class OperacaoBancariaController {
    private final DepositoService depositoService;
    private final SaqueService saqueService;

    @PostMapping("/deposito")
    public ResponseEntity<TransacaoDTO> depositar(@RequestBody CriarDepositoDTO criarDepositoDTO) {
        return ResponseEntity.ok(
                depositoService.realizarOperacao(criarDepositoDTO)
        );
    }

    @PostMapping("/saque")
    public ResponseEntity<TransacaoDTO> sacar(@RequestBody CriaSaqueDTO criaSaqueDTO) {
        return ResponseEntity.ok(
                saqueService.realizarOperacao(criaSaqueDTO)
        );
    }
}
