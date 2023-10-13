package com.example.MeuAlmoxarifado.controller.dto.fornecedora.response;


import com.example.MeuAlmoxarifado.domain.model.Fornecedora;

public record ListFornecedoraDTO(

        Long id,

        String cnpj,

        String razaoSocial,

        String nomeFantasia,

        String fone
) {
    public ListFornecedoraDTO(Fornecedora fornecedora) {
        this(fornecedora.getId(), fornecedora.getCnpj(), fornecedora.getRazaoSocial(), fornecedora.getNomeFantasia(), fornecedora.getFone());
    }
}
