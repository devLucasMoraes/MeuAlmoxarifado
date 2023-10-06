package com.example.MeuAlmoxarifado.controller.transacaoSaida.dto.request;

import com.example.MeuAlmoxarifado.controller.itemTransacoesSaida.dto.request.NewItemTransacaoSaidaDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public record NewTransacaoSaidaDTO(

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale = "pt_BR")
        LocalDateTime dataRequisicao,
        BigDecimal valorTotal,

        String obs,

        String ordemProducao,

        @NotNull
        Long idRequisitante,

        @NotNull
        Long idDestino,

        @Valid
        @NotNull
        @NotEmpty
        ArrayList<NewItemTransacaoSaidaDTO> itens
) {
}
