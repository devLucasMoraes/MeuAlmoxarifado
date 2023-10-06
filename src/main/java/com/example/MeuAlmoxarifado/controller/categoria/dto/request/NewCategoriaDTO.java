package com.example.MeuAlmoxarifado.controller.categoria.dto.request;

import com.example.MeuAlmoxarifado.controller.conversaoDeConsumo.dto.request.NewConversaoDeConsumoDTO;
import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;

public record NewCategoriaDTO(
        @NotBlank
        String nome,

        @NotNull
        Unidade undEstoque,

        @NotNull
        BigDecimal estoqueMinimo,

        ArrayList<NewConversaoDeConsumoDTO> conversoesDeConsumo
) {
}
