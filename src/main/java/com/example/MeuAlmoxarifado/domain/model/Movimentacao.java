package com.example.MeuAlmoxarifado.domain.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacaoes")
@NoArgsConstructor
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    private Material material;

    private LocalDateTime data;

    private BigDecimal valorUnitMed;

    private BigDecimal quantidade;

    private BigDecimal valorTotal;

    private String justificativa;


}
