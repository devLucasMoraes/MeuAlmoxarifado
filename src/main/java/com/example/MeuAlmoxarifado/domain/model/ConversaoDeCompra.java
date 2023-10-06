package com.example.MeuAlmoxarifado.domain.model;

import com.example.MeuAlmoxarifado.controller.conversaoDeCompra.dto.request.EditConversaoDeCompraDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "conversoes_de_compra")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ConversaoDeCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "und_compra")
    private Unidade undCompra;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "und_estoque")
    private Unidade undEstoque;

    @JoinColumn(name = "fator_de_conversao")
    private BigDecimal fatorDeConversao;

    @ManyToOne
    @JoinColumn(name = "vinculos_materiais_fornecedoras_id")
    private VinculoMaterialComFornecedora vinculoComFornecedoras;

    public void update(EditConversaoDeCompraDTO conversaoAtualizada, VinculoMaterialComFornecedora vinculo) {
        this.vinculoComFornecedoras = vinculo;

        if(conversaoAtualizada.undCompra() != null) {
            this.undCompra = conversaoAtualizada.undCompra();
        }
        if(conversaoAtualizada.undCompra() != null) {
            this.undEstoque = conversaoAtualizada.undEstoque();
        }
        if(conversaoAtualizada.fatorDeConversao() != null) {
            this.fatorDeConversao = conversaoAtualizada.fatorDeConversao();
        }
    }
}
