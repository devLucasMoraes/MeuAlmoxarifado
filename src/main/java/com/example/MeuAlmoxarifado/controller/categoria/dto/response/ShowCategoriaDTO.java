package com.example.MeuAlmoxarifado.controller.categoria.dto.response;


import com.example.MeuAlmoxarifado.controller.conversaoDeConsumo.dto.response.ShowConversaoDeConsumoDTO;
import com.example.MeuAlmoxarifado.domain.model.Categoria;
import com.example.MeuAlmoxarifado.domain.model.Unidade;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
                categoria.getConversoesDeConsumo()
                        .stream()
                        .map(ShowConversaoDeConsumoDTO::new)
                        .collect(Collectors.toList()));
    }
}
