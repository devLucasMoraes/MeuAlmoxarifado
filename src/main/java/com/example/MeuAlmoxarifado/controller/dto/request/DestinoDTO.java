package com.example.MeuAlmoxarifado.controller.dto.request;

import com.example.MeuAlmoxarifado.domain.model.Destino;
import jakarta.validation.constraints.NotBlank;

public record DestinoDTO(
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
