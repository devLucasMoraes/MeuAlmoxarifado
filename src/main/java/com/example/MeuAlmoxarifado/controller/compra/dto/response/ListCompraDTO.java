package com.example.MeuAlmoxarifado.controller.compra.dto.response;

import com.example.MeuAlmoxarifado.controller.itemDeCompra.dto.response.ListItemDeCompraDTO;
import com.example.MeuAlmoxarifado.domain.model.ItemDeCompra;
import com.example.MeuAlmoxarifado.domain.model.NfeDeCompra;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ListCompraDTO(

        Long id,

        String nfe,

        LocalDateTime dataEmissao,

        LocalDateTime dataRecebimento,

        BigDecimal valorTotal,

        BigDecimal valorFrete,

        BigDecimal valorIpiTotal,

        String obs,

        Long idTransportadora,

        Long idFornecedora,

        List<ListItemDeCompraDTO> itens
) {
    public ListCompraDTO(NfeDeCompra nfeDeCompra) {
        this(
                nfeDeCompra.getId(),
                nfeDeCompra.getChaveNfe(),
                nfeDeCompra.getDataEmissao(),
                nfeDeCompra.getDataRecebimento(),
                nfeDeCompra.getValorTotalNfe(),
                nfeDeCompra.getValorFrete(),
                nfeDeCompra.getValorTotalIpi(),
                nfeDeCompra.getObs(),
                nfeDeCompra.getTransportadora().getId(),
                nfeDeCompra.getFornecedora().getId(),
                toDTO(nfeDeCompra.getItens())
        );
    }

    private static List<ListItemDeCompraDTO> toDTO(List<ItemDeCompra> itens) {
        return itens.stream().map(item -> new ListItemDeCompraDTO(
                item.getMaterial().getId(),
                item.getUndCom(),
                item.getQuantCom(),
                item.getValorUntCom(),
                item.getValorIpi()
        )).collect(Collectors.toList());
    }
}
