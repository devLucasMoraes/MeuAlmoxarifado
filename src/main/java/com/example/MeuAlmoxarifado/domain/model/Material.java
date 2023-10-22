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
@ToString
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "descricao")
    private String descricao;

    @JoinColumn(name = "valor_unt_med_auto")
    private Boolean valorUntMedAuto = true;

    @JoinColumn(name = "valor_unt_med")
    private BigDecimal valorUntMed = BigDecimal.ZERO;

    @JoinColumn(name = "qtd_em_estoque")
    private BigDecimal qtdEmEstoque = BigDecimal.ZERO;

    @JoinColumn(name = "qtd_a_receber")
    private BigDecimal qtdAReceber = BigDecimal.ZERO;

    @JoinColumn(name = "qtd_a_pagar")
    private BigDecimal qtdAPagar = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "categorias_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<VinculoMaterialComFornecedora> fornecedorasVinculadas;

    public Material(Long id) {
        this.id = id;
    }


}
