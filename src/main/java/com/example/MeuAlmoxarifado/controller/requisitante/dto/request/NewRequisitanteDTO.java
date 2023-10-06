package com.example.MeuAlmoxarifado.controller.requisitante.dto.request;

import jakarta.validation.constraints.NotBlank;

public record NewRequisitanteDTO(

        @NotBlank
        String nome,

        @NotBlank
        String fone
) {
}
