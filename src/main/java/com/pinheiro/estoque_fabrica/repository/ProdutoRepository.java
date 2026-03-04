package com.pinheiro.estoque_fabrica.repository;
import com.pinheiro.estoque_fabrica.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    Optional<Produto> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}
