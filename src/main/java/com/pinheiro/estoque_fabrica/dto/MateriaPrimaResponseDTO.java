package com.pinheiro.estoque_fabrica.dto;

import java.util.UUID;

public record MateriaPrimaResponseDTO(
        UUID id,
        String nome,
        Double quantidadeEmEstoque
) {}
