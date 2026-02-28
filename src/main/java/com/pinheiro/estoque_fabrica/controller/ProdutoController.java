package com.pinheiro.estoque_fabrica.controller;
import com.pinheiro.estoque_fabrica.dto.CriarProdutoDTO;
import com.pinheiro.estoque_fabrica.dto.ProdutoResponseDTO;
import com.pinheiro.estoque_fabrica.service.ProdutoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    public ProdutoResponseDTO criar(@RequestBody CriarProdutoDTO dto) {
        return service.criar(dto);
    }

    @GetMapping
    public List<ProdutoResponseDTO> listar() {
        return service.listar();
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        service.deletar(id);
    }
}