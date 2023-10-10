package com.example.MeuAlmoxarifado.controller.dto.itemDeCompra.response;

import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ListItemDeCompraDTO(

        @NotNull
        Long idMaterial,

        Unidade undCom,

        @Positive(message = "deve ser maior que zero")
        BigDecimal quantCom,

        BigDecimal valorUntCom,

        BigDecimal valorIpi
) {
}
