package com.example.MeuAlmoxarifado.controller.dto.material.response;



import com.example.MeuAlmoxarifado.controller.dto.conversaoDeCompra.response.ShowConversaoDeCompraDTO;
import com.example.MeuAlmoxarifado.controller.dto.itemDeCompra.response.ShowItemDeCompraDTO;
import com.example.MeuAlmoxarifado.controller.dto.vinculoMaterialComFornecedora.response.ShowVinculoComFornecedorasDTO;
import com.example.MeuAlmoxarifado.domain.model.Material;
import com.example.MeuAlmoxarifado.domain.model.VinculoMaterialComFornecedora;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record ShowMaterialDTO(
        Long id,

        String descricao,

        BigDecimal valorUnt,

        BigDecimal qtdEmEstoque,

        Long idCategoria,

        List<ShowVinculoComFornecedorasDTO> fornecedorasVinculadas
) {
    public ShowMaterialDTO(Material model) {
        this(
                model.getId(),
                model.getDescricao(),
                model.getValorUntMed(),
                model.getQtdEmEstoque(),
                model.getCategoria().getId(),
                ofNullable(model.getFornecedorasVinculadas()).orElse(emptyList()).stream().map(ShowVinculoComFornecedorasDTO::new).collect(toList())
        );
    }
}
