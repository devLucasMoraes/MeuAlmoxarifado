package com.example.MeuAlmoxarifado.controller.dto.transportadora.response;

import com.example.MeuAlmoxarifado.domain.model.Transportadora;

public record ListTransportadoraDTO(

        Long id,

        String cnpj,

        String razaoSocial,

        String nomeFantasia,

        String fone
) {
    public ListTransportadoraDTO(Transportadora transportadora) {
        this(transportadora.getId(), transportadora.getCnpj(), transportadora.getRazaoSocial(), transportadora.getNomeFantasia(), transportadora.getFone());
    }
}
