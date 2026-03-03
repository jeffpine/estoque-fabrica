package com.pinheiro.estoque_fabrica.controller;
import com.pinheiro.estoque_fabrica.domain.Produto;
import com.pinheiro.estoque_fabrica.dto.CriarProdutoDTO;
import com.pinheiro.estoque_fabrica.dto.ProdutoResponseDTO;
import com.pinheiro.estoque_fabrica.service.ProdutoService;
import org.springframework.http.ResponseEntity;
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

    @PutMapping
    public Produto atualizar(
            @RequestBody Produto produto){
        return service.atualizar(produto);
    }

    @DeleteMapping("/deletar/{nome}")
    public ResponseEntity<Void> deletarProduto(@PathVariable String nome) {
        service.deletarProduto(nome);
        return ResponseEntity.noContent().build();
    }
}