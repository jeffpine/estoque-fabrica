package com.pinheiro.estoque_fabrica.service;

import com.pinheiro.estoque_fabrica.domain.MateriaPrima;
import com.pinheiro.estoque_fabrica.dto.CriarMateriaPrimaDTO;
import com.pinheiro.estoque_fabrica.dto.MateriaPrimaResponseDTO;
import com.pinheiro.estoque_fabrica.repository.MateriaPrimaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MateriaPrimaService {

    private final MateriaPrimaRepository repository;

    public MateriaPrimaService(MateriaPrimaRepository repository) {
        this.repository = repository;
    }

    public MateriaPrimaResponseDTO criar(CriarMateriaPrimaDTO dto) {

        String nomeNormalizado = dto.nome().trim();

        Optional<MateriaPrima> existenteOpt =
                repository.findByNomeIgnoreCase(nomeNormalizado);

        if (existenteOpt.isPresent()) {

            MateriaPrima existente = existenteOpt.get();

            existente.setQuantidadeEmEstoque(
                    existente.getQuantidadeEmEstoque() + dto.quantidadeEmEstoque()
            );

            repository.save(existente);

            return new MateriaPrimaResponseDTO(
                    existente.getId(),
                    existente.getNome(),
                    existente.getQuantidadeEmEstoque()
            );
        }

        MateriaPrima materia = new MateriaPrima();
        materia.setNome(nomeNormalizado);
        materia.setQuantidadeEmEstoque(dto.quantidadeEmEstoque());

        repository.save(materia);

        return new MateriaPrimaResponseDTO(
                materia.getId(),
                materia.getNome(),
                materia.getQuantidadeEmEstoque()
        );
    }
    public List<MateriaPrimaResponseDTO> listar() {

        Map<String, Double> agrupado = repository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        m -> m.getNome().toLowerCase(),
                        Collectors.summingDouble(MateriaPrima::getQuantidadeEmEstoque)
                ));

        return agrupado.entrySet()
                .stream()
                .map(entry -> new MateriaPrimaResponseDTO(
                        null,
                        entry.getKey().substring(0,1).toUpperCase() + entry.getKey().substring(1),
                        entry.getValue()
                ))
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
