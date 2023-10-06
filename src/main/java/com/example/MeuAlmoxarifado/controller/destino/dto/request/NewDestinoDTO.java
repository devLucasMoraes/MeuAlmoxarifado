package com.example.MeuAlmoxarifado.controller.destino.dto.request;

import com.example.MeuAlmoxarifado.domain.model.Destino;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewDestinoDTO(
        @NotNull
        Long id,
        @NotBlank
        String nome,
        @NotBlank
        String fone) {
    public Destino toModel() {
        Destino model = new Destino();
        model.setId(this.id);
        model.setNome(this.nome);
        model.setFone(this.fone);
        return model;
    }
}
