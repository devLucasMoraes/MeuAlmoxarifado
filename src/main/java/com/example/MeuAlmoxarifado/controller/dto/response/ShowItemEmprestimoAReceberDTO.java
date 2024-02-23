package com.example.MeuAlmoxarifado.controller.dto.response;

import com.example.MeuAlmoxarifado.domain.model.ItemEmprestimoAReceber;
import com.example.MeuAlmoxarifado.domain.model.Unidade;

import java.math.BigDecimal;

public record ShowItemEmprestimoAReceberDTO(
        Long idItem,
        Long idMaterial,
        Unidade unidade,
        BigDecimal quantEntregue,
        BigDecimal valorUnt) {
    public ShowItemEmprestimoAReceberDTO(ItemEmprestimoAReceber model) {
        this(
                model.getId(),
                model.getMaterial().getId(),
                model.getUnidade(),
                model.getQuantidade(),
                model.getValorUnitario()
        );
    }
}
