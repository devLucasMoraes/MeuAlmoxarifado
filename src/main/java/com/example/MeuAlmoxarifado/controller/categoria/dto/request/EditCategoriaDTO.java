package com.example.MeuAlmoxarifado.controller.categoria.dto.request;

import com.example.MeuAlmoxarifado.controller.conversaoDeConsumo.dto.request.EditConversaoDeConsumoDTO;
import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;

public record EditCategoriaDTO(

        @NotBlank
        String nome,

        @NotNull
        Unidade undEstoque,

        @NotNull
        BigDecimal estoqueMinimo,

        ArrayList<EditConversaoDeConsumoDTO> conversoesDeConsumo

) {
}
