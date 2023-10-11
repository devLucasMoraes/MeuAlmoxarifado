package com.example.MeuAlmoxarifado.controller.dto.itemTransacoesSaida.request;

import com.example.MeuAlmoxarifado.domain.model.ItemTransacaoSaida;
import com.example.MeuAlmoxarifado.domain.model.Material;
import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ItemTransacaoSaidaDTO(
        Long id,
        @NotNull
        Long idMaterial,
        @NotNull
        Unidade undConsumo,
        @Positive(message = "deve ser maior que zero")
        BigDecimal quantEntregue,
        BigDecimal valorUntEntregue) {
    public ItemTransacaoSaida toModel() {
        ItemTransacaoSaida model = new ItemTransacaoSaida();
        model.setId(this.id);
        model.setMaterial(new Material(this.idMaterial));
        model.setUndConsumo(this.undConsumo);
        model.setQuantEnt(this.quantEntregue);
        model.setValorUntEnt(valorUntEntregue);
        return model;
    }
}
