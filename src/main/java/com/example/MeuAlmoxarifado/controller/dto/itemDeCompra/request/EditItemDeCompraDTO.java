package com.example.MeuAlmoxarifado.controller.dto.itemDeCompra.request;

import com.example.MeuAlmoxarifado.domain.model.ItemDeCompra;
import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record EditItemDeCompraDTO(
        @NotNull
        Long idMaterial,
        @NotNull
        Unidade undCom,
        @NotNull
        @Positive(message = "deve ser maior que zero")
        BigDecimal quantCom,
        @NotNull
        BigDecimal valorUntCom,
        @NotNull
        BigDecimal valorIpi,
        String descricaoFornecedor,
        String referenciaFornecedor
) {
    public ItemDeCompra toModel() {
        ItemDeCompra model = new ItemDeCompra();
        model.setUndCom(this.undCom);
        model.setQuantCom(this.quantCom);
        model.setValorUntCom(this.valorUntCom);
        model.setValorIpi(this.valorIpi);
        model.setDescricaoFornecedor(this.descricaoFornecedor);
        model.setReferenciaFornecedor(this.referenciaFornecedor);
        return model;
    }
}
