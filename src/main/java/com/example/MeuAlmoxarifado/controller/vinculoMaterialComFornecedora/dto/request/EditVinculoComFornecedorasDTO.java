package com.example.MeuAlmoxarifado.controller.vinculoMaterialComFornecedora.dto.request;


import com.example.MeuAlmoxarifado.controller.conversaoDeCompra.dto.request.EditConversaoDeCompraDTO;
import com.example.MeuAlmoxarifado.domain.model.VinculoMaterialComFornecedora;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record EditVinculoComFornecedorasDTO(


        Long id,

        Long idFornecedora,

        String codProd,

        List<EditConversaoDeCompraDTO> conversoesDeCompra) {
    public VinculoMaterialComFornecedora toModel() {
        VinculoMaterialComFornecedora model = new VinculoMaterialComFornecedora();
        model.setCodProd(this.codProd);
        model.setConversaoDeCompras(ofNullable(this.conversoesDeCompra)
                .orElse(emptyList())
                .stream().map(EditConversaoDeCompraDTO::toModel).collect(toList()));
        return model;
    }
}
