package com.example.MeuAlmoxarifado.domain.model;

import com.example.MeuAlmoxarifado.controller.destino.dto.request.EditDestinoDTO;
import com.example.MeuAlmoxarifado.controller.destino.dto.request.NewDestinoDTO;
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

    public Destino(NewDestinoDTO dados) {
        this.nome = dados.nome();
        this.fone = dados.fone();
    }

    public void update(EditDestinoDTO dados) {
        if(dados.nome() != null) {
            this.nome = dados.nome();
        }
        if(dados.fone() != null) {
            this.fone = dados.fone();
        }
    }
}
