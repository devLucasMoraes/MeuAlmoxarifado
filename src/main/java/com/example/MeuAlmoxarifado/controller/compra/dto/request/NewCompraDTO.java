package com.example.MeuAlmoxarifado.controller.compra.dto.request;

import com.example.MeuAlmoxarifado.controller.itemDeCompra.dto.request.NewItemDeCompraDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public record NewCompraDTO(

        String nfe,

        String chaveNfe,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale = "pt_BR")
        LocalDateTime dataEmissao,
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale = "pt_BR")
        LocalDateTime dataRecebimento,

        BigDecimal valorTotalProdutos,
        BigDecimal valorFrete,
        BigDecimal valorTotalIpi,
        BigDecimal valorSeguro,
        BigDecimal valorDesconto,
        BigDecimal valorTotalNfe,
        BigDecimal valorOutros,

        String obs,

        @NotNull
        Long idTransportadora,

        @NotNull
        Long idFornecedora,

        @Valid
        @NotNull
        @NotEmpty
        ArrayList<NewItemDeCompraDTO> itens
) {
}
