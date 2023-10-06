package com.example.MeuAlmoxarifado.domain.model;

import com.example.MeuAlmoxarifado.controller.conversaoDeConsumo.dto.request.EditConversaoDeConsumoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "conversoes_de_consumo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ConversaoDeConsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "und_consumo")
    private Unidade undConsumo;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "und_estoque")
    private Unidade undEstoque;

    @JoinColumn(name = "fator_de_conversao")
    private BigDecimal fatorDeConversao;

    @ManyToOne
    @JoinColumn(name = "categorias_id")
    private Categoria categoria;

    public void update(EditConversaoDeConsumoDTO conversaoAtualizada, Categoria categoria) {
        this.categoria = categoria;
        this.undEstoque = categoria.getUndEstoque();
        if(conversaoAtualizada.undConsumo() != null) {
            this.undConsumo = conversaoAtualizada.undConsumo();
        }
        if(conversaoAtualizada.fatorDeConversao() != null) {
            this.fatorDeConversao = conversaoAtualizada.fatorDeConversao();
        }
    }
}
