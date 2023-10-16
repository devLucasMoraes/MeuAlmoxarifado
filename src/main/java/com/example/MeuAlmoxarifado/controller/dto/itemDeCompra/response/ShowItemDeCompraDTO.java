package com.example.MeuAlmoxarifado.controller.dto.itemDeCompra.response;

import com.example.MeuAlmoxarifado.domain.model.ItemDeCompra;
import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ShowItemDeCompraDTO(
        Long id,
        Long idMaterial,
        Unidade undCom,
        BigDecimal quantCom,
        BigDecimal valorUntCom,
        BigDecimal valorIpi,
        String descricaoFornecedor,
        String referenciaFornecedor) {
    public ShowItemDeCompraDTO(ItemDeCompra model) {
        this(
                model.getId(),
                model.getMaterial().getId(),
                model.getUndCom(),
                model.getQuantCom(),
                model.getValorUntCom(),
                model.getValorIpi(),
                model.getDescricaoFornecedor(),
                model.getReferenciaFornecedor()
        );
    }
}
