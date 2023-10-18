package com.example.MeuAlmoxarifado.controller.dto.response;


import com.example.MeuAlmoxarifado.domain.model.VinculoMaterialComFornecedora;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record ShowVinculoComFornecedorasDTO(
        Long id,
        Long idFornecedora,
        String referenciaFornecedora,
        String descricaoFornecedora,
        List<ShowConversaoDeCompraDTO> conversoesDeCompra) {

    public ShowVinculoComFornecedorasDTO(VinculoMaterialComFornecedora model) {
        this(
                model.getId(),
                model.getFornecedora().getId(),
                model.getReferenciaFornecedora(),
                model.getDescricaoFornecedora(),
                ofNullable(model.getConversaoDeCompras()).orElse(emptyList()).stream().map(ShowConversaoDeCompraDTO::new).collect(toList())

        );
    }
}
