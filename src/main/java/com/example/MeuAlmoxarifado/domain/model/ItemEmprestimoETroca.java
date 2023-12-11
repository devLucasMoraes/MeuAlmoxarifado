package com.example.MeuAlmoxarifado.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_emprestimo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemEmprestimoETroca extends BaseItem {

    @ManyToOne
    @JoinColumn(name = "emprestimos_e_trocas_id")
    private EmprestimoETroca emprestimoETroca;

}
