package com.example.MeuAlmoxarifado.controller.dto.request;

import com.example.MeuAlmoxarifado.domain.model.ItemEmprestimoETroca;
import com.example.MeuAlmoxarifado.domain.model.ItemRequisicao;
import com.example.MeuAlmoxarifado.domain.model.Material;
import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ItemEmprestimoETrocaDTO(
        Long idItem,
        @NotNull
        Long idMaterial,
        @NotNull
        Unidade unidade,
        @Positive(message = "deve ser maior que zero")
        BigDecimal quantEntregue,
        BigDecimal valorUnt) {
    public ItemEmprestimoETroca toModel() {
        ItemEmprestimoETroca model = new ItemEmprestimoETroca();
        model.setId(this.idItem);
        model.setMaterial(new Material(this.idMaterial));
        model.setUndidade(this.unidade);
        model.setQuantEnt(this.quantEntregue);
        model.setValorUnt(this.valorUnt);
        return model;
    }

    public ItemEmprestimoETroca toNewModel() {
        ItemEmprestimoETroca model = new ItemEmprestimoETroca();
        model.setMaterial(new Material(this.idMaterial));
        model.setUndidade(this.unidade);
        model.setQuantEnt(this.quantEntregue);
        model.setValorUnt(this.valorUnt);
        return model;
    }
}
