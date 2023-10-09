package com.example.MeuAlmoxarifado.controller.transacaoSaida.dto.request;

import com.example.MeuAlmoxarifado.controller.itemTransacoesSaida.dto.request.EditItemTransacaoSaidaDTO;
import com.example.MeuAlmoxarifado.domain.model.TransacaoSaida;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record EditTransacaoSaidaDTO(
        Long id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale = "pt_BR")
        LocalDateTime dataRequisicao,
        BigDecimal valorTotal,
        String obs,
        String ordemProducao,
        Long idRequisitante,
        Long idDestino,
        @Valid
        @NotEmpty
        @NotNull
        List<EditItemTransacaoSaidaDTO> itens) {
    public TransacaoSaida toModel() {
        TransacaoSaida model = new TransacaoSaida();
        model.setDataRequisicao(this.dataRequisicao);
        model.setValorTotal(this.valorTotal);
        model.setObs(this.obs);
        model.setOrdemProducao(this.ordemProducao);
        model.setItens(ofNullable(this.itens)
                .orElse(emptyList())
                .stream().map(EditItemTransacaoSaidaDTO::toModel).collect(toList()));
        return model;
    }
}
