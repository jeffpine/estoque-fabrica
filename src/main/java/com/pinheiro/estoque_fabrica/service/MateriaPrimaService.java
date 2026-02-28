package com.pinheiro.estoque_fabrica.service;

import com.pinheiro.estoque_fabrica.domain.MateriaPrima;
import com.pinheiro.estoque_fabrica.dto.CriarMateriaPrimaDTO;
import com.pinheiro.estoque_fabrica.dto.MateriaPrimaResponseDTO;
import com.pinheiro.estoque_fabrica.repository.MateriaPrimaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MateriaPrimaService {

    private final MateriaPrimaRepository repository;

    public MateriaPrimaService(MateriaPrimaRepository repository) {
        this.repository = repository;
    }

    public MateriaPrimaResponseDTO criar(CriarMateriaPrimaDTO dto) {

        MateriaPrima materia = new MateriaPrima();
        materia.setNome(dto.nome());
        materia.setQuantidadeEmEstoque(dto.quantidadeEmEstoque());

        repository.save(materia);

        return new MateriaPrimaResponseDTO(
                materia.getId(),
                materia.getNome(),
                materia.getQuantidadeEmEstoque()
        );
    }
     public List<MateriaPrimaResponseDTO> listar() {
        return repository.findAll()
                .stream()
                .map(m -> new MateriaPrimaResponseDTO(
                        m.getId(),
                        m.getNome(),
                        m.getQuantidadeEmEstoque()))
                .toList();
     }
     public MateriaPrimaResponseDTO atualizar(UUID id, CriarMateriaPrimaDTO dto) {

        MateriaPrima materia = repository.findById(id)
                .orElseThrow();
        materia.setNome(dto.nome());
        materia.setQuantidadeEmEstoque(dto.quantidadeEmEstoque());

        repository.save(materia);

        return new MateriaPrimaResponseDTO(
                materia.getId(),
                materia.getNome(),
                materia.getQuantidadeEmEstoque()
        );
     }
     public void deletar(UUID id) {
        repository.deleteById(id);
     }
}
