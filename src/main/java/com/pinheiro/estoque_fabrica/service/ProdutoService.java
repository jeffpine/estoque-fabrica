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
        produto.setNome(dto.nome());
        produto.setValor(dto.valor());

        List<ProdutoMateriaPrima> composicao = dto.composicao()
                .stream()
                .map(item -> {

                    MateriaPrima materia = materiaPrimaRepository
                            .findById(item.materiaPrimaId())
                            .orElseThrow();

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
                                        c.getMateriaPrima().getId(),
                                        c.getQuantidadeNecessaria()))
                                .toList()))
                .toList();
    }

    public void deletar(UUID id) {
        produtoRepository.deleteById(id);
    }
}