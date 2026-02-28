package com.pinheiro.estoque_fabrica.domain;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "produto_materias_primas")
public class ProdutoMateriaPrima {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_prima_id")
    private MateriaPrima materiaPrima;

    @Column(nullable = false)
    private Double quantidadeNecessaria;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public MateriaPrima getMateriaPrima() {
        return materiaPrima;
    }

    public void setMateriaPrima(MateriaPrima materiaPrima) {
        this.materiaPrima = materiaPrima;
    }

    public Double getQuantidadeNecessaria() {
        return quantidadeNecessaria;
    }

    public void setQuantidadeNecessaria(Double quantidadeNecessaria) {
        this.quantidadeNecessaria = quantidadeNecessaria;
    }
}