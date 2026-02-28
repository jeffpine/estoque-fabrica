package com.pinheiro.estoque_fabrica.dto;

import java.util.List;

public record CriarProdutoDTO(
        String nome,
        Double valor,
        List<ItemComposicaoDTO> composicao
) {}
