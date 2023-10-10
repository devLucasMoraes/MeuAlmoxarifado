package com.example.MeuAlmoxarifado.controller.dto.conversaoDeConsumo.request;

import com.example.MeuAlmoxarifado.domain.model.ConversaoDeConsumo;
import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record NewConversaoDeConsumoDTO(
        @NotNull
        Unidade undConsumo,

        Unidade undEstoque,
        @NotNull
        BigDecimal fatorDeConversao) {
    public ConversaoDeConsumo toModel(){
        ConversaoDeConsumo model = new ConversaoDeConsumo();
        model.setUndConsumo(this.undConsumo);
        model.setUndEstoque(this.undEstoque);
        model.setFatorDeConversao(this.fatorDeConversao);
        return model;
    }
}
