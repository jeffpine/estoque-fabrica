package com.pinheiro.estoque_fabrica.controller;

import com.pinheiro.estoque_fabrica.dto.CriarMateriaPrimaDTO;
import com.pinheiro.estoque_fabrica.dto.MateriaPrimaResponseDTO;
import com.pinheiro.estoque_fabrica.service.MateriaPrimaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/materias-primas")
public class MateriaPrimaController {

    private final MateriaPrimaService service;

    public MateriaPrimaController(MateriaPrimaService service) {
        this.service = service;
    }

    @PostMapping
    public MateriaPrimaResponseDTO criar(@RequestBody CriarMateriaPrimaDTO dto) {
        return service.criar(dto);
    }

    @GetMapping
    public List<MateriaPrimaResponseDTO> listar() {
        return service.listar();
    }

    @PutMapping("/{id}")
    public MateriaPrimaResponseDTO atualizar(
            @PathVariable UUID id,
            @RequestBody CriarMateriaPrimaDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        service.deletar(id);
    }
}