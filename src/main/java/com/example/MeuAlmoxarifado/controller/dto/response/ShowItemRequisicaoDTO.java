package com.example.MeuAlmoxarifado.controller.dto.response;

import com.example.MeuAlmoxarifado.domain.model.ItemRequisicao;
import com.example.MeuAlmoxarifado.domain.model.Unidade;

import java.math.BigDecimal;

public record ShowItemRequisicaoDTO(
        Long idItem,
        Long idMaterial,
        Unidade undConsumo,
        BigDecimal quantEntregue,
        BigDecimal valorUntEntregue) {
    public ShowItemRequisicaoDTO(ItemRequisicao model) {
        this(
                model.getId(),
                model.getMaterial().getId(),
                model.getUnidade(),
                model.getQuantidade(),
                model.getValorUnitario()
        );
    }
}
