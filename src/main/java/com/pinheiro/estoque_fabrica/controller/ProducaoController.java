package com.pinheiro.estoque_fabrica.controller;
import com.pinheiro.estoque_fabrica.dto.PlanoProducaoDTO;
import com.pinheiro.estoque_fabrica.service.OtimizacaoProducaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producao")
public class ProducaoController {

    private final OtimizacaoProducaoService service;

    public ProducaoController(OtimizacaoProducaoService service) {
        this.service = service;
    }
    @GetMapping("/otimizar")
    public PlanoProducaoDTO otimizar() {
        return service.otimizarProducao();
    }

}
