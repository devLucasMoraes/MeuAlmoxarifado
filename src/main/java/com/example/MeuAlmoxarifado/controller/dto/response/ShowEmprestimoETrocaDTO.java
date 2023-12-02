package com.example.MeuAlmoxarifado.controller.dto.response;

import com.example.MeuAlmoxarifado.controller.dto.request.ItemEmprestimoETrocaDTO;
import com.example.MeuAlmoxarifado.domain.model.EmprestimoETroca;
import com.example.MeuAlmoxarifado.domain.model.Situacao;
import com.example.MeuAlmoxarifado.domain.model.Tipo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record ShowEmprestimoETrocaDTO(
        Long id,
        Tipo tipo,
        LocalDateTime data,
        BigDecimal valorTotal,
        Long idFornecedora,
        Situacao situacao,
        List<ShowItemEmprestimoETrocaDTO> itens) {
    public ShowEmprestimoETrocaDTO(EmprestimoETroca model) {
        this(
                model.getId(),
                model.getTipo(),
                model.getData(),
                model.getValorTotal(),
                model.getFornecedora().getId(),
                model.getSituacao(),
                ofNullable(model.getItensEmprestimo()).orElse(emptyList()).stream().map(ShowItemEmprestimoETrocaDTO::new).collect(toList())
        );
    }
}
