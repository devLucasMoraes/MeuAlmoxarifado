package com.example.MeuAlmoxarifado.controller.conversaoDeConsumo.dto.request;


import com.example.MeuAlmoxarifado.domain.model.Unidade;

import java.math.BigDecimal;

public record EditConversaoDeConsumoDTO(

        Unidade undConsumo,

        Unidade undEstoque,

        BigDecimal fatorDeConversao

) {
}
