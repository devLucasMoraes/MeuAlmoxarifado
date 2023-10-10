package com.example.MeuAlmoxarifado.controller.dto.compra.response;

import com.example.MeuAlmoxarifado.controller.dto.itemDeCompra.response.ShowItemDeCompraDTO;
import com.example.MeuAlmoxarifado.domain.model.ItemDeCompra;
import com.example.MeuAlmoxarifado.domain.model.NfeDeCompra;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ShowCompraDTO(

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

        List<ShowItemDeCompraDTO> itens
) {
    public ShowCompraDTO(NfeDeCompra nfeDeCompra) {
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

    private static List<ShowItemDeCompraDTO> toDTO(List<ItemDeCompra> itens) {
        return itens.stream().map(item -> new ShowItemDeCompraDTO(
                item.getMaterial().getId(),
                item.getUndCom(),
                item.getQuantCom(),
                item.getValorUntCom(),
                item.getValorIpi(),
                item.getDescricaoFornecedor(),
                item.getReferenciaFornecedor()
        )).collect(Collectors.toList());
    }

}
