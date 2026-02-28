package com.pinheiro.estoque_fabrica.service;

import com.pinheiro.estoque_fabrica.domain.MateriaPrima;
import com.pinheiro.estoque_fabrica.domain.Produto;
import com.pinheiro.estoque_fabrica.domain.ProdutoMateriaPrima;
import com.pinheiro.estoque_fabrica.dto.PlanoProducaoDTO;
import com.pinheiro.estoque_fabrica.repository.MateriaPrimaRepository;
import com.pinheiro.estoque_fabrica.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OtimizacaoProducaoService {

    private final ProdutoRepository produtoRepository;
    private final MateriaPrimaRepository materiaPrimaRepository;

    public OtimizacaoProducaoService(ProdutoRepository produtoRepository,
                                     MateriaPrimaRepository materiaPrimaRepository) {
        this.produtoRepository = produtoRepository;
        this.materiaPrimaRepository = materiaPrimaRepository;
    }

    public PlanoProducaoDTO otimizarProducao(){
        // Lógica para otimizar a produção
        List<Produto> produtos = this.produtoRepository.findAll();
        List<MateriaPrima> estoque = this.materiaPrimaRepository.findAll();

        produtos.sort(Comparator.comparing(Produto::getValor).reversed());

        Map<UUID,Double> estoqueDisponivel = estoque.stream()
                .collect(Collectors.toMap(MateriaPrima::getId, MateriaPrima::getQuantidadeEmEstoque));

        Map<String, Integer> plano = new LinkedHashMap<>();
        Double valorTotal = 0.0;

        for (Produto produto : produtos) {

            int unidadesPossiveis = calcularMaximoUnidades(produto, estoqueDisponivel);

            if (unidadesPossiveis > 0) {
                plano.put(produto.getNome(), unidadesPossiveis);
                valorTotal += unidadesPossiveis * produto.getValor();
                baixarEstoque(produto, unidadesPossiveis, estoqueDisponivel);
            }
        }
        return new  PlanoProducaoDTO(plano, valorTotal);
    }
    private int calcularMaximoUnidades(Produto produto, Map<UUID, Double> estoque) {

        return produto.getComposicao().stream()
                .mapToInt(item ->
                        (int) (estoque.get(item.getMateriaPrima().getId())
                                / item.getQuantidadeNecessaria()))
                .min()
                .orElse(0);
    }

    private void baixarEstoque(Produto produto, int quantidade,
                               Map<UUID, Double> estoque) {

        for (ProdutoMateriaPrima item : produto.getComposicao()) {

            UUID idMateriaPrima = item.getMateriaPrima().getId();
            double consumo = item.getQuantidadeNecessaria() * quantidade;

            estoque.put(idMateriaPrima,
                    estoque.get(idMateriaPrima) - consumo);
        }
    }
}