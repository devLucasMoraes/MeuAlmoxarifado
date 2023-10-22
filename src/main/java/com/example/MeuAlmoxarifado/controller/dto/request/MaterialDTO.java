package com.example.MeuAlmoxarifado.controller.dto.request;

import com.example.MeuAlmoxarifado.domain.model.Categoria;
import com.example.MeuAlmoxarifado.domain.model.Material;
import jakarta.validation.Valid;
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
        @NotNull
        Boolean valorUntMedAuto,
        BigDecimal valorUntMed,
        @NotNull
        Long idCategoria,
        @Valid
        List<VinculoComFornecedorasDTO> fornecedorasVinculadas) {
    public Material toModel() {
        Material model = new Material();
        model.setId(this.id);
        model.setDescricao(this.descricao);
        model.setValorUntMedAuto(this.valorUntMedAuto);
        if(!this.valorUntMedAuto) {
            model.setValorUntMed(this.valorUntMed);
        }
        model.setCategoria(new Categoria(this.idCategoria));
        model.setFornecedorasVinculadas(ofNullable(this.fornecedorasVinculadas)
                .orElse(emptyList())
                .stream().map(VinculoComFornecedorasDTO::toModel).collect(toList()));
        return model;
    }
}
