package com.pinheiro.estoque_fabrica.dto;

import java.util.UUID;

public record ItemComposicaoDTO(
        UUID materiaPrimaId,
        Double quantidadeNecessaria
) {}
