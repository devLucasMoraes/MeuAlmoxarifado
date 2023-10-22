package com.example.MeuAlmoxarifado.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_emprestimo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemEmprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movimentacoes_id")
    private Emprestimo emprestimo;

    @ManyToOne
    @JoinColumn(name = "materiais_id")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "transacoes_saida_id")
    private Fornecedora fornecedora;

    @JoinColumn(name = "quant")
    private BigDecimal quantEnt;

    @JoinColumn(name = "valor_unt")
    private BigDecimal valorUnt;


}
