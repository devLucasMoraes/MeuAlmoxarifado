package com.example.MeuAlmoxarifado.controller.dto.transacaoSaida.response;



import com.example.MeuAlmoxarifado.controller.dto.itemTransacoesSaida.response.ListItemTransacaoSaidaDTO;
import com.example.MeuAlmoxarifado.domain.model.ItemTransacaoSaida;
import com.example.MeuAlmoxarifado.domain.model.TransacaoSaida;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ListTransacaoSaidaDTO(

        Long id,

        LocalDateTime dataRequisicao,

        BigDecimal valorTotal,

        String obs,

        String ordemProducao,

        Long idRequisitante,

        Long idDestino,

        List<ListItemTransacaoSaidaDTO> itens
) {
    public ListTransacaoSaidaDTO(TransacaoSaida transacaoSaida) {
        this(
                transacaoSaida.getId(),
                transacaoSaida.getDataRequisicao(),
                transacaoSaida.getValorTotal(),
                transacaoSaida.getOrdemProducao(),
                transacaoSaida.getObs(),
                transacaoSaida.getRequisitante().getId(),
                transacaoSaida.getDestino().getId(),
                toDTO(transacaoSaida.getItens())
        );
    }

    private static List<ListItemTransacaoSaidaDTO> toDTO(List<ItemTransacaoSaida> itens) {
        return itens.stream().map(item -> new ListItemTransacaoSaidaDTO(
                item.getMaterial().getId(),
                item.getUndConsumo(),
                item.getQuantEnt(),
                item.getValorUntEnt()
        )).collect(Collectors.toList());
    }
}
