package com.example.MeuAlmoxarifado.controller.itemTransacoesSaida.dto.request;

import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record NewItemTransacaoSaidaDTO(

        @NotNull
        Long idMaterial,

        @NotNull
        Unidade undConsumo,

        @Positive(message = "deve ser maior que zero")
        BigDecimal quantEntregue,

        BigDecimal valorUntEntregue
) {
        public NewItemTransacaoSaidaDTO(@Valid EditItemTransacaoSaidaDTO itemAtualizado) {
                this(itemAtualizado.idMaterial(),itemAtualizado.undConsumo(),itemAtualizado.quantEntregue(),itemAtualizado.valorUntEntregue());
        }
}
