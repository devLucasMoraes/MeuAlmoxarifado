package com.example.MeuAlmoxarifado.controller.vinculoMaterialComFornecedora.dto.request;

import com.example.MeuAlmoxarifado.controller.conversaoDeCompra.dto.request.NewConversaoDeCompraDTO;
import com.example.MeuAlmoxarifado.domain.model.VinculoMaterialComFornecedora;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record NewVinculoComFornecedorasDTO(

        @NotNull
        Long idFornecedora,

        @NotBlank
        String codProd,

        List<NewConversaoDeCompraDTO> conversoesDeCompra
) {
    public NewVinculoComFornecedorasDTO(EditVinculoComFornecedorasDTO vinculoAtualizado) {
        this(vinculoAtualizado.idFornecedora(), vinculoAtualizado.codProd(), new ArrayList<>());
    }

    public VinculoMaterialComFornecedora toModel() {
        VinculoMaterialComFornecedora model = new VinculoMaterialComFornecedora();
        model.setCodProd(this.codProd);
        model.setConversaoDeCompras(ofNullable(this.conversoesDeCompra)
                .orElse(emptyList())
                .stream().map(NewConversaoDeCompraDTO::toModel).collect(toList()));
        return model;
    }
}
