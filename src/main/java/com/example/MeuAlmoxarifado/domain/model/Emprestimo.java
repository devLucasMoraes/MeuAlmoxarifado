package com.example.MeuAlmoxarifado.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "movimentacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Emprestimo {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime data;

    private BigDecimal valorTotal;

    @ManyToOne
    private Fornecedora fornecedora;

    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    @OneToMany(mappedBy = "emprestimo", cascade = CascadeType.ALL)
    private List<ItemEmprestimo> itensEmprestimo;
}
