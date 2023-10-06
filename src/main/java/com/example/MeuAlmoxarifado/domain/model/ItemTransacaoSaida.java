package com.example.MeuAlmoxarifado.domain.model;


import com.example.MeuAlmoxarifado.controller.itemTransacoesSaida.dto.request.EditItemTransacaoSaidaDTO;
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


    public void update(EditItemTransacaoSaidaDTO itemAtualizado, Material material, TransacaoSaida transacaoSaida) {
        this.material = material;
        this.transacaoSaida = transacaoSaida;
        if(itemAtualizado.undConsumo() != null) {
            this.undConsumo = itemAtualizado.undConsumo();
        }
        if(itemAtualizado.valorUntEntregue() != null) {
            this.valorUntEnt = itemAtualizado.valorUntEntregue();
        }
        if(itemAtualizado.quantEntregue() != null) {
            this.quantEnt = itemAtualizado.quantEntregue();
        }
    }

    public BigDecimal getQuantEstoque() {

        if (undConsumo == material.getCategoria().getUndEstoque()) {
            return quantEnt;
        } else {
            ConversaoDeConsumo conversaoDeConsumo = this.material.getCategoria().getConversoesDeConsumo().stream()
                    .filter(conversao -> conversao.getUndConsumo().equals(undConsumo))
                    .findFirst()
                    .orElseThrow(() -> new ValidacaoException("Conversão de " + material.getCategoria().getUndEstoque() + " para " + undConsumo + " não encontrada"));

            return quantEnt.multiply(conversaoDeConsumo.getFatorDeConversao());

        }
    }

    public BigDecimal getValorTotal() {
        return this.quantEnt.multiply(this.valorUntEnt);
    }
}
