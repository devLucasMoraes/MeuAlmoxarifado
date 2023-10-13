package com.example.MeuAlmoxarifado.controller.dto.conversaoDeCompra.request;

import com.example.MeuAlmoxarifado.domain.model.ConversaoDeCompra;
import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ConversaoDeCompraDTO(
        Long id,
        @NotNull
        Unidade undCompra,
        @NotNull
        Unidade undEstoque,
        @NotNull
        BigDecimal fatorDeConversao) {
    public ConversaoDeCompra toModel() {
        ConversaoDeCompra model = new ConversaoDeCompra();
        model.setId(this.id);
        model.setUndEstoque(this.undEstoque);
        model.setUndCompra(this.undCompra);
        model.setFatorDeConversao(this.fatorDeConversao);
        return model;
    }
}
