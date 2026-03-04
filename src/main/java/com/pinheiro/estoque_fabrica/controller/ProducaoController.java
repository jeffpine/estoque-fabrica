package com.pinheiro.estoque_fabrica.controller;
import com.pinheiro.estoque_fabrica.dto.PlanoProducaoDTO;
import com.pinheiro.estoque_fabrica.dto.PlanoProducaoManualDTO;
import com.pinheiro.estoque_fabrica.service.OtimizacaoProducaoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producao")
public class ProducaoController {

    private final OtimizacaoProducaoService service;

    public ProducaoController(OtimizacaoProducaoService service) {
        this.service = service;
    }

    @GetMapping("/optimizar")
    public PlanoProducaoDTO optimizar() {
        return service.otimizarProducao();
    }

    @PostMapping("/manual")
    public PlanoProducaoDTO planoManual(@RequestBody PlanoProducaoManualDTO dto) {
        return service.calcularPlanoManual(dto);
    }
}
