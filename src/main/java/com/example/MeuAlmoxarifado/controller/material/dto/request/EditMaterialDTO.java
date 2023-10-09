package com.example.MeuAlmoxarifado.controller.material.dto.request;


import com.example.MeuAlmoxarifado.controller.vinculoMaterialComFornecedora.dto.request.EditVinculoComFornecedorasDTO;
import com.example.MeuAlmoxarifado.domain.model.Material;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record EditMaterialDTO(


        Long id,

        String descricao,

        BigDecimal valorUnt,

        Long idCategoria,

        List<EditVinculoComFornecedorasDTO> fornecedorasVinculadas) {
    public Material toModel() {
        Material model = new Material();
        model.setDescricao(this.descricao);
        model.setValorUnt(this.valorUnt);
        model.setFornecedorasVinculadas(ofNullable(this.fornecedorasVinculadas)
                .orElse(emptyList())
                .stream().map(EditVinculoComFornecedorasDTO::toModel).collect(toList()));
        return model;
    }
}
