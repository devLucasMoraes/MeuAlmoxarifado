package com.example.MeuAlmoxarifado.controller.dto.response;


import com.example.MeuAlmoxarifado.domain.model.LocalDeAplicacao;

public record ShowLocalDeAplicacaoDTO(
        Long id,
        String nome) {
    public ShowLocalDeAplicacaoDTO(LocalDeAplicacao localDeAplicacao) {
        this(localDeAplicacao.getId(), localDeAplicacao.getNome());
    }
}
