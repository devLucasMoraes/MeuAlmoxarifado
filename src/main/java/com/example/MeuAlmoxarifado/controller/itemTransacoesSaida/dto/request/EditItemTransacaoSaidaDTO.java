package com.example.MeuAlmoxarifado.controller.itemTransacoesSaida.dto.request;

import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record EditItemTransacaoSaidaDTO(

        @NotNull
        Long idMaterial,

        Unidade undConsumo,

        @Positive(message = "deve ser maior que zero")
        BigDecimal quantEntregue,

        BigDecimal valorUntEntregue
) {
}
