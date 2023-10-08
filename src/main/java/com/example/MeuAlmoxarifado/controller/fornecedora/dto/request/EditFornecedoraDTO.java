package com.example.MeuAlmoxarifado.controller.fornecedora.dto.request;

import com.example.MeuAlmoxarifado.domain.model.Fornecedora;

public record EditFornecedoraDTO(

        Long id,

        String cnpj,

        String razaoSocial,

        String nomeFantasia,

        String fone) {
    public Fornecedora toModel() {
        Fornecedora model = new Fornecedora();
        model.setId(this.id);
        model.setCnpj(this.cnpj);
        model.setRazaoSocial(this.razaoSocial);
        model.setNomeFantasia(this.nomeFantasia);
        model.setFone(this.fone);
        return model;
    }
}
