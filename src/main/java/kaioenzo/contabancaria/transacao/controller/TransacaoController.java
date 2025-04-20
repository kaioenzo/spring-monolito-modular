package kaioenzo.contabancaria.transacao.controller;

import kaioenzo.contabancaria.transacao.TransacaoDTO;
import kaioenzo.contabancaria.transacao.service.TransacaoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor
public class TransacaoController {
    private final TransacaoServiceImpl transacaoServiceImpl;


    @GetMapping("/{id}")
    public ResponseEntity<TransacaoDTO> buscar(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(transacaoServiceImpl.buscar(id));
    }
}
