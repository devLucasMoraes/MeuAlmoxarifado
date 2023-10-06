package com.example.MeuAlmoxarifado.controller.conversaoDeConsumo.dto.request;

import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record NewConversaoDeConsumoDTO(

        @NotNull
        Unidade undConsumo,

        @NotNull
        BigDecimal fatorDeConversao

) {
    public NewConversaoDeConsumoDTO(EditConversaoDeConsumoDTO conversaoAtualizada) {
        this(conversaoAtualizada.undConsumo(), conversaoAtualizada.fatorDeConversao());
    }
}
