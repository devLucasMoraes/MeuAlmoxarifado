package com.example.MeuAlmoxarifado.controller.conversaoDeCompra.dto.request;


import com.example.MeuAlmoxarifado.domain.model.ConversaoDeCompra;
import com.example.MeuAlmoxarifado.domain.model.Unidade;

import java.math.BigDecimal;

public record EditConversaoDeCompraDTO(

        Unidade undCompra,

        Unidade undEstoque,

        BigDecimal fatorDeConversao

) {
    public ConversaoDeCompra toModel() {
        ConversaoDeCompra model = new ConversaoDeCompra();
        model.setUndEstoque(this.undEstoque);
        model.setUndCompra(this.undCompra);
        model.setFatorDeConversao(this.fatorDeConversao);
        return model;
    }
}
