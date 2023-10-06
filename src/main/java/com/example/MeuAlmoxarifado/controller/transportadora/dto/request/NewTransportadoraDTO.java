package com.example.MeuAlmoxarifado.controller.transportadora.dto.request;

import jakarta.validation.constraints.NotBlank;

public record NewTransportadoraDTO(

        @NotBlank
        String cnpj,

        @NotBlank
        String razaoSocial,

        String nomeFantasia,

        String fone
) {
}
