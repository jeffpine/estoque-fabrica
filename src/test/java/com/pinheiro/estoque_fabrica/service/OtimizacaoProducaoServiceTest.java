package com.pinheiro.estoque_fabrica.service;

import com.pinheiro.estoque_fabrica.domain.MateriaPrima;
import com.pinheiro.estoque_fabrica.domain.Produto;
import com.pinheiro.estoque_fabrica.domain.ProdutoMateriaPrima;
import com.pinheiro.estoque_fabrica.repository.MateriaPrimaRepository;
import com.pinheiro.estoque_fabrica.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OtimizacaoProducaoServiceTest {

    @Test
    void devePriorizarProdutoMaisRentavel() {

        ProdutoRepository produtoRepository = mock(ProdutoRepository.class);
        MateriaPrimaRepository materiaPrimaRepository = mock(MateriaPrimaRepository.class);

        // Matérias-primas
        MateriaPrima ferro = new MateriaPrima();
        ferro.setId(UUID.randomUUID());
        ferro.setNome("Ferro");
        ferro.setQuantidadeEmEstoque(100.0);

        MateriaPrima madeira = new MateriaPrima();
        madeira.setId(UUID.randomUUID());
        madeira.setNome("Madeira");
        madeira.setQuantidadeEmEstoque(50.0);

        when(materiaPrimaRepository.findAll())
                .thenReturn(List.of(ferro, madeira));

        // Produto A
        Produto produtoA = new Produto();
        produtoA.setNome("Produto A");
        produtoA.setValor(100.0);

        ProdutoMateriaPrima compA = new ProdutoMateriaPrima();
        compA.setProduto(produtoA);
        compA.setMateriaPrima(ferro);
        compA.setQuantidadeNecessaria(10.0);

        produtoA.setComposicao(List.of(compA));

        // Produto B
        Produto produtoB = new Produto();
        produtoB.setNome("Produto B");
        produtoB.setValor(80.0);

        ProdutoMateriaPrima compB1 = new ProdutoMateriaPrima();
        compB1.setProduto(produtoB);
        compB1.setMateriaPrima(ferro);
        compB1.setQuantidadeNecessaria(5.0);

        ProdutoMateriaPrima compB2 = new ProdutoMateriaPrima();
        compB2.setProduto(produtoB);
        compB2.setMateriaPrima(madeira);
        compB2.setQuantidadeNecessaria(10.0);

        produtoB.setComposicao(List.of(compB1, compB2));

        when(produtoRepository.findAll())
                .thenReturn(List.of(produtoA, produtoB));

        OtimizacaoProducaoService service =
                new OtimizacaoProducaoService(produtoRepository, materiaPrimaRepository);

        var resultado = service.otimizarProducao();

        assertEquals(1000.0, resultado.getValorTotal());
        assertEquals(10, resultado.getProdutos().get("Produto A"));
    }
}