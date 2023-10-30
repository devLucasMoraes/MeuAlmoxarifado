package com.example.MeuAlmoxarifado.controller.dto.response;


import com.example.MeuAlmoxarifado.domain.model.ConversaoDeConsumo;
import com.example.MeuAlmoxarifado.domain.model.Unidade;

import java.math.BigDecimal;

public record ShowConversaoDeConsumoDTO(
        Long idConversao,
        Unidade undConsumo,
        Unidade undEstoque,
        BigDecimal fatorDeConversao

) {
    public ShowConversaoDeConsumoDTO(ConversaoDeConsumo conversaoDeConsumo) {
        this(conversaoDeConsumo.getId(), conversaoDeConsumo.getUndConsumo(), conversaoDeConsumo.getUndEstoque(), conversaoDeConsumo.getFatorDeConversao());
    }
}
