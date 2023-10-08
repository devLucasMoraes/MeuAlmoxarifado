package com.example.MeuAlmoxarifado.controller.compra.dto.request;

import com.example.MeuAlmoxarifado.controller.itemDeCompra.dto.request.NewItemDeCompraDTO;
import com.example.MeuAlmoxarifado.domain.model.NfeDeCompra;
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
        List<NewItemDeCompraDTO> itens) {
    public NfeDeCompra toModel() {
        NfeDeCompra model = new NfeDeCompra();
        model.setNfe(this.nfe);
        model.setChaveNfe(this.chaveNfe);
        model.setDataEmissao(this.dataEmissao);
        model.setDataRecebimento(this.dataRecebimento);
        model.setValorTotalProdutos(this.valorTotalProdutos);
        model.setValorFrete(this.valorFrete);
        model.setValorTotalIpi(this.valorTotalIpi);
        model.setValorSeguro(this.valorSeguro);
        model.setValorDesconto(this.valorDesconto);
        model.setValorTotalNfe(this.valorTotalNfe);
        model.setValorOutros(this.valorOutros);
        model.setObs(this.obs);
        model.setItens(ofNullable(this.itens)
                .orElse(emptyList())
                .stream().map(NewItemDeCompraDTO::toModel).collect(toList()));
        return model;
    }
}
