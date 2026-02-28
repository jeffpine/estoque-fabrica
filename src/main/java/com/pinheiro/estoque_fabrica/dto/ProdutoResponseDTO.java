package com.pinheiro.estoque_fabrica.dto;

import java.util.List;
import java.util.UUID;

public record ProdutoResponseDTO(
        UUID id,
        String nome,
        Double valor,
        List<ItemComposicaoDTO> composicao
) {}
