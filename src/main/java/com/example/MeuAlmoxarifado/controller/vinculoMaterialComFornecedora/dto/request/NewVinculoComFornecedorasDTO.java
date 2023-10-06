package com.example.MeuAlmoxarifado.controller.vinculoMaterialComFornecedora.dto.request;

import com.example.MeuAlmoxarifado.controller.conversaoDeCompra.dto.request.NewConversaoDeCompraDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

public record NewVinculoComFornecedorasDTO(

        @NotNull
        Long idFornecedora,

        @NotBlank
        String codProd,

        ArrayList<NewConversaoDeCompraDTO> conversoesDeCompra
) {
    public NewVinculoComFornecedorasDTO(EditVinculoComFornecedorasDTO vinculoAtualizado) {
        this(vinculoAtualizado.idFornecedora(), vinculoAtualizado.codProd(), new ArrayList<>());
    }
}
