package com.example.MeuAlmoxarifado.controller.dto.response;

import com.example.MeuAlmoxarifado.domain.model.ItemEmprestimoETroca;
import com.example.MeuAlmoxarifado.domain.model.ItemRequisicao;
import com.example.MeuAlmoxarifado.domain.model.Unidade;

import java.math.BigDecimal;

public record ShowItemEmprestimoETrocaDTO(
        Long idItem,
        Long idMaterial,
        Unidade undConsumo,
        BigDecimal quantEntregue,
        BigDecimal valorUntEntregue) {
    public ShowItemEmprestimoETrocaDTO(ItemEmprestimoETroca model) {
        this(
                model.getId(),
                model.getMaterial().getId(),
                model.getUndidade(),
                model.getQuantEnt(),
                model.getValorUnt()
        );
    }
}
