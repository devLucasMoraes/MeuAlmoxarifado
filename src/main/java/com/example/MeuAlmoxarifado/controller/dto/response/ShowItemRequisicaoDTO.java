package com.example.MeuAlmoxarifado.controller.dto.response;

import com.example.MeuAlmoxarifado.domain.model.ItemRequisicao;
import com.example.MeuAlmoxarifado.domain.model.Unidade;

import java.math.BigDecimal;

public record ShowItemRequisicaoDTO(
        Long id,
        Long idMaterial,
        Unidade undConsumo,
        BigDecimal quantEntregue,
        BigDecimal valorUntEntregue) {
    public ShowItemRequisicaoDTO(ItemRequisicao model) {
        this(
                model.getId(),
                model.getMaterial().getId(),
                model.getUndConsumo(),
                model.getQuantEntregue(),
                model.getValorUntEnt()
        );
    }
}
