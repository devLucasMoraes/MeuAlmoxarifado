package com.example.MeuAlmoxarifado.controller.material.dto.request;

import com.example.MeuAlmoxarifado.controller.vinculoMaterialComFornecedora.dto.request.NewVinculoComFornecedorasDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;

public record NewMaterialDTO(
        @NotBlank
        String descricao,
    
        BigDecimal valorUnt,
        @NotNull
        Long idCategoria,

        ArrayList<NewVinculoComFornecedorasDTO> fornecedorasVinculadas
) {
}
