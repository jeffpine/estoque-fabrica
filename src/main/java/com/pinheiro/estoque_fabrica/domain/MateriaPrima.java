package com.pinheiro.estoque_fabrica.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "materias_primas")
public class MateriaPrima {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Double quantidadeEmEstoque;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(Double quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }
}
