package com.example.MeuAlmoxarifado.controller.conversaoDeConsumo.dto.response;


import com.example.MeuAlmoxarifado.domain.model.ConversaoDeConsumo;
import com.example.MeuAlmoxarifado.domain.model.Unidade;

import java.math.BigDecimal;

public record ShowConversaoDeConsumoDTO(

        Unidade undConsumo,

        Unidade undEstoque,

        BigDecimal fatorDeConversao

) {
    public ShowConversaoDeConsumoDTO(ConversaoDeConsumo conversaoDeConsumo) {
        this(conversaoDeConsumo.getUndConsumo(), conversaoDeConsumo.getUndEstoque(), conversaoDeConsumo.getFatorDeConversao());
    }
}
