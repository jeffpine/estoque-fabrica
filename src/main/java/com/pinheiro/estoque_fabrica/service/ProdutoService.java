package com.pinheiro.estoque_fabrica.service;

import com.pinheiro.estoque_fabrica.domain.MateriaPrima;
import com.pinheiro.estoque_fabrica.domain.Produto;
import com.pinheiro.estoque_fabrica.domain.ProdutoMateriaPrima;
import com.pinheiro.estoque_fabrica.dto.CriarProdutoDTO;
import com.pinheiro.estoque_fabrica.dto.ItemComposicaoDTO;
import com.pinheiro.estoque_fabrica.dto.ProdutoResponseDTO;
import com.pinheiro.estoque_fabrica.repository.MateriaPrimaRepository;
import com.pinheiro.estoque_fabrica.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final MateriaPrimaRepository materiaPrimaRepository;

    public ProdutoService(ProdutoRepository produtoRepository,
                          MateriaPrimaRepository materiaPrimaRepository) {
        this.produtoRepository = produtoRepository;
        this.materiaPrimaRepository = materiaPrimaRepository;
    }

    public ProdutoResponseDTO criar(CriarProdutoDTO dto) {

        Produto produto = new Produto();
        produto.setNome(dto.nome().trim().toLowerCase());
        produto.setValor(dto.valor());

        if (produtoRepository.existsByNomeIgnoreCase((produto.getNome()))) {
            throw new RuntimeException("Produto já cadastrado");
        }

        List<ProdutoMateriaPrima> composicao = dto.composicao()
                .stream()
                .map(item -> {

                    MateriaPrima materia = materiaPrimaRepository
                            .findByNomeIgnoreCase(item.materiaPrimaNome())
                            .stream()
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Matéria prima não encontrada"));

                    ProdutoMateriaPrima pm = new ProdutoMateriaPrima();
                    pm.setProduto(produto);
                    pm.setMateriaPrima(materia);
                    pm.setQuantidadeNecessaria(item.quantidadeNecessaria());

                    return pm;
                }).toList();

        produto.setComposicao(composicao);

        produtoRepository.save(produto);

        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getValor(),
                dto.composicao()
        );
    }

    public List<ProdutoResponseDTO> listar() {
        return produtoRepository.findAll()
                .stream()
                .map(p -> new ProdutoResponseDTO(
                        p.getId(),
                        p.getNome(),
                        p.getValor(),
                        p.getComposicao().stream()
                                .map(c -> new ItemComposicaoDTO(
                                        c.getMateriaPrima().getNome(),
                                        c.getQuantidadeNecessaria()))
                                .toList()))
                .toList();
    }
    public Produto atualizar(Produto produto){

        Produto existente = produtoRepository
                .findByNomeIgnoreCase(produto.getNome())
                .orElseThrow();

        existente.setValor(produto.getValor());

        return produtoRepository.save(existente);
    }

    public void deletarProduto(String nome){

        String nomeNormalizado = nome.trim().toLowerCase();

        Produto produto = produtoRepository
                .findByNomeIgnoreCase(nomeNormalizado)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produtoRepository.delete(produto);

    }
}