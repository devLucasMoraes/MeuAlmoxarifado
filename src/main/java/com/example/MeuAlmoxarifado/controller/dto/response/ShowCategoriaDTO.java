package com.example.MeuAlmoxarifado.controller.dto.response;


import com.example.MeuAlmoxarifado.domain.model.Categoria;
import com.example.MeuAlmoxarifado.domain.model.Unidade;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record ShowCategoriaDTO(
        Long id,

        String nome,

        Unidade undEstoque,

        BigDecimal estoqueMinimo,

        List<ShowConversaoDeConsumoDTO> conversoesDeConsumo
) {
    public ShowCategoriaDTO(Categoria categoria) {
        this(
                categoria.getId(),
                categoria.getNome(),
                categoria.getUndEstoque(),
                categoria.getEstoqueMinimo(),
                ofNullable(categoria.getConversoesDeConsumo())
                        .orElse(emptyList())
                        .stream().map(ShowConversaoDeConsumoDTO::new).collect(toList()));
    }
}
