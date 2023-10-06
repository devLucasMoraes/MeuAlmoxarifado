package com.example.MeuAlmoxarifado.controller.itemTransacoesSaida.dto.response;

import com.example.MeuAlmoxarifado.domain.model.Unidade;

import java.math.BigDecimal;

public record ShowItemTransacaoSaidaDTO(

        Long idMaterial,

        Unidade undConsumo,

        BigDecimal quantEntregue,

        BigDecimal valorUntEntregue
) {
}
