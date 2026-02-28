package com.pinheiro.estoque_fabrica.repository;

import com.pinheiro.estoque_fabrica.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
}
