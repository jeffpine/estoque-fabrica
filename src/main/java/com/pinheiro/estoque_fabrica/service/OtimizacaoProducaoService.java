package com.pinheiro.estoque_fabrica.service;

import com.pinheiro.estoque_fabrica.domain.Produto;
import com.pinheiro.estoque_fabrica.domain.MateriaPrima;
import com.pinheiro.estoque_fabrica.domain.ProdutoMateriaPrima;
import com.pinheiro.estoque_fabrica.dto.ItemProducaoDTO;
import com.pinheiro.estoque_fabrica.dto.PlanoProducaoDTO;
import com.pinheiro.estoque_fabrica.dto.PlanoProducaoManualDTO;
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

    public PlanoProducaoDTO otimizarProducao() {
        List<Produto> produtos = produtoRepository.findAll();
        List<MateriaPrima> estoque = materiaPrimaRepository.findAll();

        // Produtos mais lucrativos primeiro
        produtos.sort(Comparator.comparing(Produto::getValor).reversed());

        Map<UUID, Double> estoqueDisponivel = estoque.stream()
                .collect(Collectors.toMap(
                        MateriaPrima::getId,
                        MateriaPrima::getQuantidadeEmEstoque
                ));

        Map<String, Integer> plano = new LinkedHashMap<>();
        double valorTotal = 0;

        for (Produto produto : produtos) {
            int unidadesPossiveis = calcularMaximoUnidades(produto, estoqueDisponivel);

            if (unidadesPossiveis <= 0) {
                continue;
            }

            plano.put(produto.getNome(), unidadesPossiveis);
            valorTotal += unidadesPossiveis * produto.getValor();

            consumirEstoque(produto, unidadesPossiveis, estoqueDisponivel);
        }

        return new PlanoProducaoDTO(plano, valorTotal);
    }

    private int calcularMaximoUnidades(Produto produto, Map<UUID, Double> estoque) {
        int maximo = Integer.MAX_VALUE;

        for (ProdutoMateriaPrima item : produto.getComposicao()) {
            UUID materiaId = item.getMateriaPrima().getId();
            double estoqueMateria = estoque.getOrDefault(materiaId, 0.0);
            int possivel = (int) (estoqueMateria / item.getQuantidadeNecessaria());

            maximo = Math.min(maximo, possivel);
        }

        return maximo == Integer.MAX_VALUE ? 0 : maximo;
    }

    public PlanoProducaoDTO calcularPlanoManual(PlanoProducaoManualDTO dto) {
        Map<String, Integer> plano = new LinkedHashMap<>();
        double valorTotal = 0;

        for (ItemProducaoDTO item : dto.itens()) {
            Produto produto = produtoRepository
                    .findByNomeIgnoreCase(item.produtoNome())
                    .orElseThrow();

            for (ProdutoMateriaPrima comp : produto.getComposicao()) {
                double necessario = comp.getQuantidadeNecessaria() * item.quantidade();

                if (comp.getMateriaPrima().getQuantidadeEmEstoque() < necessario) {
                    throw new RuntimeException("Estoque insuficiente para produzir " + produto.getNome());
                }
            }

            plano.put(produto.getNome(), item.quantidade());
            valorTotal += produto.getValor() * item.quantidade();
        }

        return new PlanoProducaoDTO(plano, valorTotal);
    }

    private void consumirEstoque(Produto produto, int quantidade, Map<UUID, Double> estoque) {
        for (ProdutoMateriaPrima item : produto.getComposicao()) {
            UUID materiaId = item.getMateriaPrima().getId();
            double consumo = item.getQuantidadeNecessaria() * quantidade;

            estoque.put(materiaId, estoque.getOrDefault(materiaId, 0.0) - consumo);
        }
    }
}