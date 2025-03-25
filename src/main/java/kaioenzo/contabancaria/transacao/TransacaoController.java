package kaioenzo.contabancaria.transacao;

import kaioenzo.contabancaria.common.interfaces.ControllerIF;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor
public class TransacaoController implements ControllerIF<Transacao, CriaTransacaoDTO> {
    private final TransacaoService transacaoService;

    @PostMapping
    @Override
    public ResponseEntity<Transacao> criar(@RequestBody CriaTransacaoDTO criaTransacaoDTO) {
        return ResponseEntity.ok().body(transacaoService.criar(criaTransacaoDTO));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Transacao> buscar(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(transacaoService.buscar(id));
    }
}
