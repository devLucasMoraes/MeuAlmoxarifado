package com.example.MeuAlmoxarifado.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fornecedoras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Fornecedora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "cnpj")
    private String cnpj;

    @JoinColumn(name = "razao_social")
    private String razaoSocial;

    @JoinColumn(name = "nome_fantasia")
    private String nomeFantasia;

    @JoinColumn(name = "fone")
    private String fone;


}
