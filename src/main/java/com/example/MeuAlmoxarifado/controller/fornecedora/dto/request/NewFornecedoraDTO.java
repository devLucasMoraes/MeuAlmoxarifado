package com.example.MeuAlmoxarifado.controller.fornecedora.dto.request;

import com.example.MeuAlmoxarifado.domain.model.Fornecedora;
import jakarta.validation.constraints.NotBlank;

public record NewFornecedoraDTO(

        @NotBlank
        String cnpj,

        @NotBlank
        String razaoSocial,

        String nomeFantasia,

        String fone) {
    public Fornecedora toModel() {
        Fornecedora model = new Fornecedora();
        model.setCnpj(this.cnpj);
        model.setRazaoSocial(this.razaoSocial);
        model.setNomeFantasia(this.nomeFantasia);
        model.setFone(this.fone);
        return model;
    }
}
