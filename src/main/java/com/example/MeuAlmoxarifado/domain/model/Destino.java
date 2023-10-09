package com.example.MeuAlmoxarifado.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "destinos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Destino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "nome")
    private String nome;

    @JoinColumn(name = "fone")
    private String fone;

}
