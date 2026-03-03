package com.pinheiro.estoque_fabrica.dto;

import java.util.UUID;

public record ItemComposicaoDTO(
        String materiaPrimaNome,
        Double quantidadeNecessaria
) {}
