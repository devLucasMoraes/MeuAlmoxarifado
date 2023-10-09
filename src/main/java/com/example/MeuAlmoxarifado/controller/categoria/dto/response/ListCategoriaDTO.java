package com.example.MeuAlmoxarifado.controller.categoria.dto.response;

import com.example.MeuAlmoxarifado.domain.model.Categoria;
import com.example.MeuAlmoxarifado.domain.model.Unidade;

import java.math.BigDecimal;

public record ListCategoriaDTO(
        Long id,
        String nome,
        Unidade undEstoque,
        BigDecimal estoqueMinimo
) {
    public ListCategoriaDTO(Categoria categoria){
        this(categoria.getId(), categoria.getNome(), categoria.getUndEstoque(), categoria.getEstoqueMinimo());
    }
}
