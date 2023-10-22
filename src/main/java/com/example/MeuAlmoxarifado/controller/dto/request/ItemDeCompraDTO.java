package com.example.MeuAlmoxarifado.controller.dto.request;

import com.example.MeuAlmoxarifado.domain.model.ItemDeCompra;
import com.example.MeuAlmoxarifado.domain.model.Material;
import com.example.MeuAlmoxarifado.domain.model.Unidade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ItemDeCompraDTO(
        Long id,
        @NotNull
        Long idMaterial,
        @NotNull
        Unidade undCom,
        @NotNull
        @Positive(message = "deve ser maior que zero")
        BigDecimal quantCom,
        @NotNull
        BigDecimal valorUntCom,
        @NotNull
        BigDecimal valorIpi,
        String descricaoFornecedora,
        String referenciaFornecedora) {
    public ItemDeCompra toModel() {
        ItemDeCompra model = new ItemDeCompra();
        model.setId(this.id);
        model.setMaterial(new Material(this.idMaterial));
        model.setUndCom(this.undCom);
        model.setQuantCom(this.quantCom);
        model.setValorUntCom(this.valorUntCom);
        model.setValorIpi(this.valorIpi);
        model.setDescricaoFornecedora(this.descricaoFornecedora);
        model.setReferenciaFornecedora(this.referenciaFornecedora);
        return model;
    }
    public ItemDeCompra toNewModel() {
        ItemDeCompra model = new ItemDeCompra();
        model.setMaterial(new Material(this.idMaterial));
        model.setUndCom(this.undCom);
        model.setQuantCom(this.quantCom);
        model.setValorUntCom(this.valorUntCom);
        model.setValorIpi(this.valorIpi);
        model.setDescricaoFornecedora(this.descricaoFornecedora);
        model.setReferenciaFornecedora(this.referenciaFornecedora);
        return model;
    }
}
