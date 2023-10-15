package com.example.MeuAlmoxarifado.controller.dto.compra.response;

import com.example.MeuAlmoxarifado.controller.dto.itemDeCompra.response.ShowItemDeCompraDTO;
import com.example.MeuAlmoxarifado.domain.model.ItemDeCompra;
import com.example.MeuAlmoxarifado.domain.model.NfeDeCompra;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record ShowCompraDTO(
        Long id,
        String nfe,
        String chaveNfe,
        LocalDateTime dataEmissao,
        LocalDateTime dataRecebimento,
        BigDecimal valorFrete,
        BigDecimal valorSeguro,
        BigDecimal valorDesconto,
        BigDecimal valorOutros,
        BigDecimal valorTotalIpi,
        BigDecimal valorTotalProdutos,
        BigDecimal valorTotalNfe,
        String obs,
        Long idTransportadora,
        Long idFornecedora,

        List<ShowItemDeCompraDTO> itens
) {
    public ShowCompraDTO(NfeDeCompra model) {
        this(
                model.getId(),
                model.getNfe(),
                model.getChaveNfe(),
                model.getDataEmissao(),
                model.getDataRecebimento(),
                model.getValorFrete(),
                model.getValorSeguro(),
                model.getValorDesconto(),
                model.getValorOutros(),
                model.getValorTotalIpi(),
                model.getValorTotalProdutos(),
                model.getValorTotalNfe(),
                model.getObs(),
                model.getTransportadora().getId(),
                model.getFornecedora().getId(),
                ofNullable(model.getItens()).orElse(emptyList()).stream().map(ShowItemDeCompraDTO::new).collect(toList())
        );
    }

}
