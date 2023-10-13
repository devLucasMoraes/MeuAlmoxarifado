package com.example.MeuAlmoxarifado.controller.dto.conversaoDeCompra.response;


import com.example.MeuAlmoxarifado.domain.model.ConversaoDeCompra;
import com.example.MeuAlmoxarifado.domain.model.Unidade;

import java.math.BigDecimal;

public record ShowConversaoDeCompraDTO(

        Unidade undCompra,

        Unidade undEstoque,

        BigDecimal fatorDeConversao

) {
    public ShowConversaoDeCompraDTO(ConversaoDeCompra conversaoDeCompras) {
        this(conversaoDeCompras.getUndCompra(), conversaoDeCompras.getUndEstoque(), conversaoDeCompras.getFatorDeConversao());
    }
}
