package com.example.MeuAlmoxarifado.controller.itemTransacoesSaida.dto.request;

import com.example.MeuAlmoxarifado.domain.model.ItemTransacaoSaida;
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
    public ItemTransacaoSaida toModel() {
        ItemTransacaoSaida model = new ItemTransacaoSaida();
        model.setUndConsumo(this.undConsumo);
        model.setQuantEnt(this.quantEntregue);
        model.setValorUntEnt(valorUntEntregue);
        return model;
    }
}
