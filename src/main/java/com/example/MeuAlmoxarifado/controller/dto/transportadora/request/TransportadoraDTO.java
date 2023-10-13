package com.example.MeuAlmoxarifado.controller.dto.transportadora.request;

import com.example.MeuAlmoxarifado.domain.model.Transportadora;
import jakarta.validation.constraints.NotBlank;

public record TransportadoraDTO(
        Long id,
        @NotBlank
        String cnpj,
        @NotBlank
        String razaoSocial,
        String nomeFantasia,
        String fone) {
    public Transportadora toModel() {
        Transportadora model = new Transportadora();
        model.setId(this.id);
        model.setNomeFantasia(this.nomeFantasia);
        model.setRazaoSocial(this.razaoSocial);
        model.setCnpj(this.cnpj);
        model.setFone(this.fone);
        return model;
    }
}
