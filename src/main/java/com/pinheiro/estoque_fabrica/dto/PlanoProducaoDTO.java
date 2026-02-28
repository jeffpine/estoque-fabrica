package com.pinheiro.estoque_fabrica.dto;

import java.util.Map;
// DTO para o plano de produção
public class PlanoProducaoDTO {

    private Map<String,Integer> produtos;
    private Double valorTotal;

    public PlanoProducaoDTO(Map<String,Integer> produtos, Double valorTotal) {
        this.produtos = produtos;
        this.valorTotal = valorTotal;
    }
    public Map<String, Integer> getProdutos() {
        return produtos;
    }
    public Double getValorTotal() {
        return valorTotal;
    }
}
