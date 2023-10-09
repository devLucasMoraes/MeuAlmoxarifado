package com.example.MeuAlmoxarifado.controller.conversaoDeConsumo.dto.request;


import com.example.MeuAlmoxarifado.domain.model.ConversaoDeConsumo;
import com.example.MeuAlmoxarifado.domain.model.Unidade;

import java.math.BigDecimal;

public record EditConversaoDeConsumoDTO(
        Long id,
        Unidade undConsumo,
        Unidade undEstoque,
        BigDecimal fatorDeConversao
) {
    public ConversaoDeConsumo toModel() {
        ConversaoDeConsumo model = new ConversaoDeConsumo();
        model.setId(this.id);
        model.setUndConsumo(this.undConsumo);
        model.setUndEstoque(this.undEstoque);
        model.setFatorDeConversao(this.fatorDeConversao);
        return model;
    }
}
