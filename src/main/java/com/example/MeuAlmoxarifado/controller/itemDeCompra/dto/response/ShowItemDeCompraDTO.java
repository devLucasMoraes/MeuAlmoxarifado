package com.example.MeuAlmoxarifado.controller.itemDeCompra.dto.response;

import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ShowItemDeCompraDTO(

        @NotNull
        Long idMaterial,

        Unidade undCom,

        @Positive(message = "deve ser maior que zero")
        BigDecimal quantCom,

        BigDecimal valorUntCom,

        BigDecimal valorIpi,

        String xProd,

        String codProd
) {
}
