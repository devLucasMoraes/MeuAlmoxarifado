package com.example.MeuAlmoxarifado.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_transacoes_entrada")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemDeCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transacoes_entrada_id")
    private NfeDeCompra nfeDeCompra;

    @ManyToOne
    @JoinColumn(name = "materiais_id")
    private Material material;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "und_com")
    private Unidade undCom;

    @JoinColumn(name = "quant_com")
    private BigDecimal quantCom;

    @JoinColumn(name = "valor_unt_com")
    private BigDecimal valorUntCom;

    @JoinColumn(name = "valor_ipi")
    private BigDecimal valorIpi;

    @JoinColumn(name = "descricao_fornecedor")
    private String descricaoFornecedor;

    @JoinColumn(name = "referencia_fornecedor")
    private String referenciaFornecedor;


}
