package com.example.MeuAlmoxarifado.domain.model;


import com.example.MeuAlmoxarifado.controller.requisitante.dto.request.EditRequisitanteDTO;
import com.example.MeuAlmoxarifado.controller.requisitante.dto.request.NewRequisitanteDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "requisitantes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Requisitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "nome")
    private String nome;

    @JoinColumn(name = "fone")
    private String fone;

    public Requisitante(NewRequisitanteDTO dados) {
        this.nome = dados.nome();
        this.fone = dados.fone();
    }

    public void update(EditRequisitanteDTO dados) {
        if(dados.nome() != null) {
            this.nome = dados.nome();
        }
        if(dados.fone() != null) {
            this.fone = dados.fone();
        }
    }
}
