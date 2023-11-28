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
public class ItemEmprestimoETroca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "emprestimos_e_trocas_id")
    private EmprestimoETroca emprestimoETroca;

    @ManyToOne
    @JoinColumn(name = "materiais_id")
    private Material material;

    @JoinColumn(name = "quant")
    private BigDecimal quantEnt;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "unidade")
    private Unidade undidade;

    @JoinColumn(name = "valor_unt")
    private BigDecimal valorUnt;


}
