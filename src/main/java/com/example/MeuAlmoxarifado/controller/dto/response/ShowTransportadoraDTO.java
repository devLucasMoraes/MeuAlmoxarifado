package com.example.MeuAlmoxarifado.controller.dto.response;

import com.example.MeuAlmoxarifado.domain.model.Transportadora;

public record ShowTransportadoraDTO(

        Long id,

        String cnpj,

        String razaoSocial,

        String nomeFantasia,

        String fone
) {
    public ShowTransportadoraDTO(Transportadora transportadora) {
        this(transportadora.getId(), transportadora.getCnpj(), transportadora.getRazaoSocial(), transportadora.getNomeFantasia(), transportadora.getFone());
    }
}
