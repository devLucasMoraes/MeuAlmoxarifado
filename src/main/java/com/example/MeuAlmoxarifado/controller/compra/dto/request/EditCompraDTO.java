package com.example.MeuAlmoxarifado.controller.compra.dto.request;

import com.example.MeuAlmoxarifado.controller.itemDeCompra.dto.request.EditItemDeCompraDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record EditCompraDTO(

        Long id,

        String nfe,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale = "pt_BR")
        LocalDateTime dataEmissao,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale = "pt_BR")
        LocalDateTime dataRecebimento,

        BigDecimal valorFrete,

        String obs,


        Long idTransportadora,


        Long idFornecedora,

        @Valid
        @NotEmpty
        @NotNull
        List<EditItemDeCompraDTO> itens
) {
}
