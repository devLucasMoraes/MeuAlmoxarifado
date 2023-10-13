package com.example.MeuAlmoxarifado.controller.dto.emprestimo;

import com.example.MeuAlmoxarifado.domain.model.Emprestimo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ShowEmprestimoDTO(
        Long id,
        LocalDateTime data,
        BigDecimal valorTotal
) {
    public ShowEmprestimoDTO(Emprestimo emprestimo) {
        this(
                emprestimo.getId(),
                emprestimo.getData(),
                emprestimo.getValorTotal()
        );
    }
}
