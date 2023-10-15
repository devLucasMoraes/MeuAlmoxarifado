package com.example.MeuAlmoxarifado.controller.dto.conversaoDeCompra.response;


import com.example.MeuAlmoxarifado.domain.model.ConversaoDeCompra;
import com.example.MeuAlmoxarifado.domain.model.Unidade;

import java.math.BigDecimal;

public record ShowConversaoDeCompraDTO(
        Long id,
        Unidade undCompra,
        Unidade undEstoque,
        BigDecimal fatorDeConversao) {
    public ShowConversaoDeCompraDTO(ConversaoDeCompra model) {
        this(model.getId(), model.getUndCompra(), model.getUndEstoque(), model.getFatorDeConversao());
    }
}
