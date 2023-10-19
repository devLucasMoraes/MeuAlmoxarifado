package com.example.MeuAlmoxarifado.controller.dto.response;


import com.example.MeuAlmoxarifado.domain.model.Local;

public record ShowLocalDTO(
        Long id,
        String nome) {
    public ShowLocalDTO(Local local) {
        this(local.getId(), local.getNome());
    }
}
