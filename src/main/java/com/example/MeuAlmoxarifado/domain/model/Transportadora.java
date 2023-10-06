package com.example.MeuAlmoxarifado.domain.model;

import com.example.MeuAlmoxarifado.controller.transportadora.dto.request.EditTransportadoraDTO;
import com.example.MeuAlmoxarifado.controller.transportadora.dto.request.NewTransportadoraDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transportadoras")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transportadora {

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

    public Transportadora(NewTransportadoraDTO dados) {
        this.cnpj = dados.cnpj();
        this.razaoSocial = dados.razaoSocial();
        this.nomeFantasia = dados.nomeFantasia();
        this.fone = dados.fone();
    }

    public void update(EditTransportadoraDTO dados) {
        if(dados.cnpj() != null) {
            this.cnpj = dados.cnpj();
        }
        if(dados.razaoSocial() != null) {
            this.razaoSocial = dados.razaoSocial();
        }
        if(dados.nomeFantasia() != null) {
            this.nomeFantasia = dados.nomeFantasia();
        }
        if(dados.fone() != null) {
            this.fone = dados.fone();
        }
    }
}
