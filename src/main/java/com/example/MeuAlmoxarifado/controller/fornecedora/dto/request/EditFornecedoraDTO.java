package com.example.MeuAlmoxarifado.controller.fornecedora.dto.request;

public record EditFornecedoraDTO(

        Long id,

        String cnpj,

        String razaoSocial,

        String nomeFantasia,

        String fone
) {
}
