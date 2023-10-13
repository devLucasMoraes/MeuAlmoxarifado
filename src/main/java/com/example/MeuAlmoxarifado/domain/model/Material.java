package com.example.MeuAlmoxarifado.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "materiais")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "descricao")
    private String descricao;

    @JoinColumn(name = "valor_unt_med")
    private BigDecimal valorUntMed;

    @JoinColumn(name = "qtd_em_estoque")
    private BigDecimal qtdEmEstoque;

    @JoinColumn(name = "qtd_a_receber")
    private BigDecimal qtdAReceber;

    @JoinColumn(name = "qtd_a_pagar")
    private BigDecimal qtdAPagar;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categorias_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VinculoMaterialComFornecedora> fornecedorasVinculadas;

    public Material(Long id) {
        this.id = id;
    }
}
