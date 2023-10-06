package com.example.MeuAlmoxarifado.controller.conversaoDeCompra.dto.request;


import com.example.MeuAlmoxarifado.domain.model.Unidade;

import java.math.BigDecimal;

public record EditConversaoDeCompraDTO(

        Unidade undCompra,

        Unidade undEstoque,

        BigDecimal fatorDeConversao

) {
}
