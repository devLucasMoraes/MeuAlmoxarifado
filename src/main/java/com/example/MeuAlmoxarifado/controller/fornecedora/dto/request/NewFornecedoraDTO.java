package com.example.MeuAlmoxarifado.controller.fornecedora.dto.request;

import jakarta.validation.constraints.NotBlank;

public record NewFornecedoraDTO(

        @NotBlank
        String cnpj,

        @NotBlank
        String razaoSocial,

        String nomeFantasia,

        String fone
) {
}
