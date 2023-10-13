package com.example.MeuAlmoxarifado.controller.exception;

public record ErrorObject(String message, String field, Object parameter) {
}
