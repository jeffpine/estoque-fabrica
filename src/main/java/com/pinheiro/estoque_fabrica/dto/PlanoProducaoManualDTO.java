package com.pinheiro.estoque_fabrica.dto;

import java.util.List;

public record PlanoProducaoManualDTO(
        List<ItemProducaoDTO> itens
) {}
