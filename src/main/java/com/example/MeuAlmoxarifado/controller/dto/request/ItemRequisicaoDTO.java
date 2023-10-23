package com.example.MeuAlmoxarifado.controller.dto.request;

import com.example.MeuAlmoxarifado.domain.model.ItemRequisicao;
import com.example.MeuAlmoxarifado.domain.model.Material;
import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ItemRequisicaoDTO(
        Long id,
        @NotNull
        Long idMaterial,
        @NotNull
        Unidade undConsumo,
        @Positive(message = "deve ser maior que zero")
        BigDecimal quantEntregue) {
    public ItemRequisicao toModel() {
        ItemRequisicao model = new ItemRequisicao();
        model.setId(this.id);
        model.setMaterial(new Material(this.idMaterial));
        model.setUndConsumo(this.undConsumo);
        model.setQuantEntregue(this.quantEntregue);
        return model;
    }

    public ItemRequisicao toNewModel() {
        ItemRequisicao model = new ItemRequisicao();
        model.setMaterial(new Material(this.idMaterial));
        model.setUndConsumo(this.undConsumo);
        model.setQuantEntregue(this.quantEntregue);
        return model;
    }
}
