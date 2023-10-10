package com.example.MeuAlmoxarifado.controller.dto.categoria.request;

import com.example.MeuAlmoxarifado.controller.dto.conversaoDeConsumo.request.NewConversaoDeConsumoDTO;
import com.example.MeuAlmoxarifado.domain.model.Categoria;
import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record NewCategoriaDTO(
        @NotBlank
        String nome,

        @NotNull
        Unidade undEstoque,

        @NotNull
        BigDecimal estoqueMinimo,

        List<NewConversaoDeConsumoDTO> conversoesDeConsumo) {
    public Categoria toModel() {
        Categoria model = new Categoria();
        model.setNome(this.nome);
        model.setUndEstoque(this.undEstoque);
        model.setEstoqueMinimo(this.estoqueMinimo);
        model.setConversoesDeConsumo(ofNullable(this.conversoesDeConsumo)
                .orElse(emptyList())
                .stream().map(NewConversaoDeConsumoDTO::toModel).collect(toList()));
        return model;
    }
}
