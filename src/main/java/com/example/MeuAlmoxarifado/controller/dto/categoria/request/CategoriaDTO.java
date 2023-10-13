package com.example.MeuAlmoxarifado.controller.dto.categoria.request;

import com.example.MeuAlmoxarifado.controller.dto.conversaoDeConsumo.request.ConversaoDeConsumoDTO;
import com.example.MeuAlmoxarifado.domain.model.Categoria;
import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record CategoriaDTO(
        Long id,
        @NotBlank
        String nome,
        @NotNull
        Unidade undEstoque,
        @NotNull
        BigDecimal estoqueMinimo,
        List<ConversaoDeConsumoDTO> conversoesDeConsumo) {
    public Categoria toModel() {
        Categoria model = new Categoria();
        model.setId(this.id);
        model.setNome(this.nome);
        model.setUndEstoque(this.undEstoque);
        model.setEstoqueMinimo(this.estoqueMinimo);
        model.setConversoesDeConsumo(ofNullable(this.conversoesDeConsumo)
                .orElse(emptyList())
                .stream().map(ConversaoDeConsumoDTO::toModel).collect(toList()));
        return model;
    }
}
