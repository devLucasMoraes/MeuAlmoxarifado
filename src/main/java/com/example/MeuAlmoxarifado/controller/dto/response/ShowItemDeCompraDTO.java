package com.example.MeuAlmoxarifado.controller.dto.response;

import com.example.MeuAlmoxarifado.domain.model.ItemDeCompra;
import com.example.MeuAlmoxarifado.domain.model.Unidade;

import java.math.BigDecimal;

public record ShowItemDeCompraDTO(
        Long idItem,
        Long idMaterial,
        Unidade undCom,
        BigDecimal quantCom,
        BigDecimal valorUntCom,
        BigDecimal valorIpi,
        String descricaoFornecedora,
        String referenciaFornecedora) {
    public ShowItemDeCompraDTO(ItemDeCompra model) {
        this(
                model.getId(),
                model.getMaterial().getId(),
                model.getUndCom(),
                model.getQuantCom(),
                model.getValorUntCom(),
                model.getValorIpi(),
                model.getDescricaoFornecedora(),
                model.getReferenciaFornecedora()
        );
    }
}
