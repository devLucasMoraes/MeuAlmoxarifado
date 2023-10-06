package com.example.MeuAlmoxarifado.domain.model;

import com.example.MeuAlmoxarifado.controller.categoria.dto.request.EditCategoriaDTO;
import com.example.MeuAlmoxarifado.controller.categoria.dto.request.NewCategoriaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorias")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "und_estoque")
    private Unidade undEstoque;

    @JoinColumn(name = "estoque_minimo")
    private BigDecimal estoqueMinimo;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConversaoDeConsumo> conversoesDeConsumo = new ArrayList<>();

    public Categoria(NewCategoriaDTO dados) {
        this.nome = dados.nome();
        this.undEstoque = dados.undEstoque();
        this.estoqueMinimo = dados.estoqueMinimo();
    }

    public void update(EditCategoriaDTO dados) {
        if(dados.nome() != null) {
            this.nome = dados.nome();
        }
        if(dados.undEstoque() != null) {
            this.undEstoque = dados.undEstoque();
        }
        if(dados.estoqueMinimo() != null) {
            this.estoqueMinimo = dados.estoqueMinimo();
        }
    }

    public void adicionarConversao(ConversaoDeConsumo conversao) {
        conversoesDeConsumo.add(conversao);
    }
}
