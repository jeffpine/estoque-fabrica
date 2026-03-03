package com.pinheiro.estoque_fabrica.repository;

import com.pinheiro.estoque_fabrica.domain.MateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MateriaPrimaRepository extends JpaRepository<MateriaPrima, UUID> {

    Optional<MateriaPrima> findByNomeIgnoreCase(String nome);



}
