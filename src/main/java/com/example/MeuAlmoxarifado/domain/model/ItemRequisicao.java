package com.example.MeuAlmoxarifado.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_requisicao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemRequisicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "materiais_id")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "requisicoes_de_estoque_id")
    private RequisicaoDeEstoque requisicaoDeEstoque;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "und_consumo")
    private Unidade undConsumo;

    @JoinColumn(name = "quant_ent")
    private BigDecimal quantEntregue;

    @JoinColumn(name = "valor_unt_ent")
    private BigDecimal valorUntEnt;


}
