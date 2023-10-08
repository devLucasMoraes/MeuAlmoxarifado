package com.example.MeuAlmoxarifado.controller.destino.dto.request;

import com.example.MeuAlmoxarifado.domain.model.Destino;
import jakarta.validation.constraints.NotBlank;

public record NewDestinoDTO(
        @NotBlank
        String nome,
        @NotBlank
        String fone) {
    public Destino toModel() {
        Destino model = new Destino();
        model.setNome(this.nome);
        model.setFone(this.fone);
        return model;
    }
}
