package com.example.MeuAlmoxarifado.controller.itemDeCompra.dto.request;

import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record NewItemDeCompraDTO(

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

        String xProd,

        String codProd
) {
        public NewItemDeCompraDTO(@Valid EditItemDeCompraDTO itemAtualizado) {
                this(itemAtualizado.idMaterial(),itemAtualizado.undCom(),itemAtualizado.quantCom(),itemAtualizado.valorUntCom(), itemAtualizado.valorIpi(), itemAtualizado.xProd(), itemAtualizado.codProd());
        }
}
