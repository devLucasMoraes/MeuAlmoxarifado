package com.example.MeuAlmoxarifado.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_transacoes_saida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemTransacaoSaida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "materiais_id")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "transacoes_saida_id")
    private TransacaoSaida transacaoSaida;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "und_consumo")
    private Unidade undConsumo;

    @JoinColumn(name = "quant_ent")
    private BigDecimal quantEnt;

    @JoinColumn(name = "valor_unt_ent")
    private BigDecimal valorUntEnt;


}
