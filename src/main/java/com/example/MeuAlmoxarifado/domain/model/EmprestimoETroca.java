package com.example.MeuAlmoxarifado.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "emprestimos_e_trocas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class EmprestimoETroca {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    private LocalDateTime data;

    private BigDecimal valorTotal;

    @ManyToOne
    private Fornecedora fornecedora;

    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    @OneToMany(mappedBy = "emprestimoETroca", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ItemEmprestimoETroca> itensEmprestimo;
}
