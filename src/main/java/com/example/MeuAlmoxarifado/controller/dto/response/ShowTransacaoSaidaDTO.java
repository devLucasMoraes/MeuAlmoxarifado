package com.example.MeuAlmoxarifado.controller.dto.response;

import com.example.MeuAlmoxarifado.domain.model.ItemTransacaoSaida;
import com.example.MeuAlmoxarifado.domain.model.TransacaoSaida;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ShowTransacaoSaidaDTO(

        Long id,

        LocalDateTime dataRequisicao,

        BigDecimal valorTotal,

        String obs,

        String ordemProducao,

        Long idRequisitante,

        Long idLocal,

        List<ShowItemTransacaoSaidaDTO> itens
) {
    public ShowTransacaoSaidaDTO(TransacaoSaida transacaoSaida) {
        this(
                transacaoSaida.getId(),
                transacaoSaida.getDataRequisicao(),
                transacaoSaida.getValorTotal(),
                transacaoSaida.getOrdemProducao(),
                transacaoSaida.getObs(),
                transacaoSaida.getRequisitante().getId(),
                transacaoSaida.getLocal().getId(),
                toDTO(transacaoSaida.getItens())
        );
    }

    private static List<ShowItemTransacaoSaidaDTO> toDTO(List<ItemTransacaoSaida> itens) {
        return itens.stream().map(item -> new ShowItemTransacaoSaidaDTO(
                item.getMaterial().getId(),
                item.getUndConsumo(),
                item.getQuantEnt(),
                item.getValorUntEnt()
        )).collect(Collectors.toList());
    }

}
