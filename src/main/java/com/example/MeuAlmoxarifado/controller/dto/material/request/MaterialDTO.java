package com.example.MeuAlmoxarifado.controller.dto.material.request;

import com.example.MeuAlmoxarifado.controller.dto.vinculoMaterialComFornecedora.request.NewVinculoComFornecedorasDTO;
import com.example.MeuAlmoxarifado.domain.model.Categoria;
import com.example.MeuAlmoxarifado.domain.model.Material;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record MaterialDTO(
        Long id,
        @NotBlank
        String descricao,
        BigDecimal valorUnt,
        @NotNull
        Long idCategoria,
        List<NewVinculoComFornecedorasDTO> fornecedorasVinculadas) {
    public Material toModel() {
        Material model = new Material();
        model.setId(this.id);
        model.setDescricao(this.descricao);
        model.setValorUnt(this.valorUnt);
        model.setCategoria(new Categoria(this.idCategoria));
        model.setFornecedorasVinculadas(ofNullable(this.fornecedorasVinculadas)
                .orElse(emptyList())
                .stream().map(NewVinculoComFornecedorasDTO::toModel).collect(toList()));
        return model;
    }
}
