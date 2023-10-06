package com.example.MeuAlmoxarifado.controller.conversaoDeCompra.dto.request;

import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record NewConversaoDeCompraDTO(

        @NotNull
        Unidade undCompra,

        @NotNull
        Unidade undEstoque,

        @NotNull
        BigDecimal fatorDeConversao

) {
    public NewConversaoDeCompraDTO(EditConversaoDeCompraDTO conversaoAtualizada) {
        this(conversaoAtualizada.undCompra(), conversaoAtualizada.undEstoque(), conversaoAtualizada.fatorDeConversao());
    }
}
