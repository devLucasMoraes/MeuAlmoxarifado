package com.example.MeuAlmoxarifado.controller.dto.request;

import com.example.MeuAlmoxarifado.domain.model.Local;
import jakarta.validation.constraints.NotBlank;

public record LocalDTO(
        Long id,
        @NotBlank
        String nome) {
    public Local toModel() {
        Local model = new Local();
        model.setId(this.id);
        model.setNome(this.nome);
        return model;
    }
}
