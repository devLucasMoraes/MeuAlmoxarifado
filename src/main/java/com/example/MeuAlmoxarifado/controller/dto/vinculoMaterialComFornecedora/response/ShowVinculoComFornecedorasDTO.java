package com.example.MeuAlmoxarifado.controller.dto.vinculoMaterialComFornecedora.response;


import com.example.MeuAlmoxarifado.controller.dto.conversaoDeCompra.response.ShowConversaoDeCompraDTO;
import com.example.MeuAlmoxarifado.domain.model.VinculoMaterialComFornecedora;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record ShowVinculoComFornecedorasDTO(
        Long id,
        Long idFornecedora,
        Long idMaterial,
        String codProd,
        List<ShowConversaoDeCompraDTO> conversoesDeCompra) {

    public ShowVinculoComFornecedorasDTO(VinculoMaterialComFornecedora model) {
        this(
                model.getId(),
                model.getFornecedora().getId(),
                model.getMaterial().getId(),
                model.getCodProd(),
                ofNullable(model.getConversaoDeCompras()).orElse(emptyList()).stream().map(ShowConversaoDeCompraDTO::new).collect(toList())

        );
    }
}
