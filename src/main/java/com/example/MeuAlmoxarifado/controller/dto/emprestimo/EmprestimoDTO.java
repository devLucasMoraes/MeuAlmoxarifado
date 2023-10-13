package com.example.MeuAlmoxarifado.controller.dto.emprestimo;

import com.example.MeuAlmoxarifado.domain.model.Emprestimo;

public record EmprestimoDTO(

) {
    public Emprestimo toModel() {
        Emprestimo model = new Emprestimo();
        return model;
    }
}
