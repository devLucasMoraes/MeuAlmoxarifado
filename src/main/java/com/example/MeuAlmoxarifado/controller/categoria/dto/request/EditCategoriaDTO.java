package com.example.MeuAlmoxarifado.controller.categoria.dto.request;

import com.example.MeuAlmoxarifado.controller.conversaoDeConsumo.dto.request.EditConversaoDeConsumoDTO;
import com.example.MeuAlmoxarifado.domain.model.Categoria;
import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record EditCategoriaDTO(
        @NotNull
        Long id,
        @NotBlank
        String nome,

        @NotNull
        Unidade undEstoque,

        @NotNull
        BigDecimal estoqueMinimo,

        List<EditConversaoDeConsumoDTO> conversoesDeConsumo

) {
    public Categoria toModel() {
        Categoria model = new Categoria();
        model.setId(this.id);
        model.setNome(this.nome);
        model.setUndEstoque(this.undEstoque);
        model.setEstoqueMinimo(this.estoqueMinimo);
        model.setConversoesDeConsumo(ofNullable(this.conversoesDeConsumo)
                .orElse(emptyList())
                .stream().map(EditConversaoDeConsumoDTO::toModel).collect(toList()));
        return model;
    }
}
