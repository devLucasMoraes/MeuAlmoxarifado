package com.example.MeuAlmoxarifado.controller.dto.request;

import com.example.MeuAlmoxarifado.domain.model.EmprestimoETroca;
import com.example.MeuAlmoxarifado.domain.model.Fornecedora;
import com.example.MeuAlmoxarifado.domain.model.Situacao;
import com.example.MeuAlmoxarifado.domain.model.Tipo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record EmprestimoETrocaDTO(
        Long id,
        @NotNull
        Tipo tipo,
        @NotNull
        LocalDateTime dataDeAbertura,
        BigDecimal valorTotal,
        @NotNull
        Long idFornecedora,
        Situacao situacao,
        @Valid
        @NotNull
        List<ItemEmprestimoETrocaDTO> itens) {
    public EmprestimoETroca toModel() {
        EmprestimoETroca model = new EmprestimoETroca();
        model.setId(this.id);
        model.setTipo(this.tipo);
        model.setData(this.dataDeAbertura);
        model.setValorTotal(this.valorTotal);
        model.setFornecedora(new Fornecedora(this.idFornecedora));
        model.setSituacao(this.situacao);
        model.setItensEmprestimo(ofNullable(this.itens)
                .orElse(emptyList())
                .stream().map(ItemEmprestimoETrocaDTO::toModel).collect(toList()));
        return model;
    }

    public EmprestimoETroca toNewModel() {
        EmprestimoETroca model = new EmprestimoETroca();
        model.setTipo(this.tipo);
        model.setData(this.dataDeAbertura);
        model.setValorTotal(this.valorTotal);
        model.setFornecedora(new Fornecedora(this.idFornecedora));
        model.setSituacao(this.situacao);
        model.setItensEmprestimo(ofNullable(this.itens)
                .orElse(emptyList())
                .stream().map(ItemEmprestimoETrocaDTO::toModel).collect(toList()));
        return model;
    }

}
