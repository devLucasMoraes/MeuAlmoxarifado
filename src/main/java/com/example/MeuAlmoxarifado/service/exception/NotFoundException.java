package com.example.MeuAlmoxarifado.service.exception;

public class NotFoundException extends BusinessException{
    public NotFoundException(String resource) {
        super("%s não encontrada.".formatted(resource));
    }
}
