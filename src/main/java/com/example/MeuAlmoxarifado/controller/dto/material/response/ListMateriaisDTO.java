package com.example.MeuAlmoxarifado.controller.dto.material.response;


import com.example.MeuAlmoxarifado.domain.model.Material;
import java.math.BigDecimal;

public record ListMateriaisDTO(

        Long id,

        String descricao,

        BigDecimal valorUnt,

        BigDecimal qtdEmEstoque,

        Long idCategoria
) {
    public ListMateriaisDTO(Material material) {
        this(material.getId(), material.getDescricao(), material.getValorUnt(), material.getQtdEmEstoque(), material.getCategoria().getId());
    }
}
