package com.example.MeuAlmoxarifado.controller.dto.response;


import com.example.MeuAlmoxarifado.domain.model.Material;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record ShowMaterialDTO(
        Long id,
        String descricao,
        Boolean valorUntMedAuto,
        BigDecimal valorUnt,
        BigDecimal qtdEmEstoque,
        Long idCategoria,
        List<ShowVinculoMaterialFornecedoraDTO> fornecedorasVinculadas) {
    public ShowMaterialDTO(Material model) {
        this(
                model.getId(),
                model.getDescricao(),
                model.getValorUntMedAuto(),
                model.getValorUntMed(),
                model.getQtdEmEstoque(),
                model.getCategoria().getId(),
                ofNullable(model.getFornecedorasVinculadas()).orElse(emptyList()).stream().map(ShowVinculoMaterialFornecedoraDTO::new).collect(toList())
        );
    }
}
