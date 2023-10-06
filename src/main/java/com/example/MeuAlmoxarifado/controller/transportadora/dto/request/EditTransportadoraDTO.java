package com.example.MeuAlmoxarifado.controller.transportadora.dto.request;

public record EditTransportadoraDTO(

        Long id,

        String cnpj,

        String razaoSocial,

        String nomeFantasia,

        String fone
) {
}
