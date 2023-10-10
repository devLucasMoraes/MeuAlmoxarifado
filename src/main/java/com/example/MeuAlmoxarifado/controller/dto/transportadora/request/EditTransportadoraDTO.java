package com.example.MeuAlmoxarifado.controller.dto.transportadora.request;

import com.example.MeuAlmoxarifado.domain.model.Transportadora;

public record EditTransportadoraDTO(

        Long id,

        String cnpj,

        String razaoSocial,

        String nomeFantasia,

        String fone) {
    public Transportadora toModel() {
        Transportadora model = new Transportadora();
        model.setId(this.id);
        model.setCnpj(this.cnpj);
        model.setRazaoSocial(this.razaoSocial);
        model.setNomeFantasia(this.nomeFantasia);
        model.setFone(this.fone);
        return model;
    }
}
