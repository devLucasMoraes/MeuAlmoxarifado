package com.example.MeuAlmoxarifado.domain.model;


import com.example.MeuAlmoxarifado.controller.material.dto.request.EditMaterialDTO;
import com.example.MeuAlmoxarifado.controller.material.dto.request.NewMaterialDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Entity
@Table(name = "materiais")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "descricao")
    private String descricao;

    @JoinColumn(name = "valor_unt")
    private BigDecimal valorUnt;

    @JoinColumn(name = "qtd_em_estoque")
    private BigDecimal qtdEmEstoque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorias_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VinculoMaterialComFornecedora> fornecedorasVinculadas = new ArrayList<>();

    public Material(NewMaterialDTO dados, Categoria categoria) {
        this.descricao = dados.descricao();
        this.valorUnt = dados.valorUnt();
        this.qtdEmEstoque = new BigDecimal(0);
        this.categoria = categoria;
        this.fornecedorasVinculadas = new ArrayList<>();
    }

    public void adicionarVinculo(VinculoMaterialComFornecedora vinculo) {
        this.fornecedorasVinculadas.add(vinculo);
    }
    public void removerVinculo(VinculoMaterialComFornecedora vinculo) {
        this.fornecedorasVinculadas.remove(vinculo);
    }

    public void update(EditMaterialDTO dados, Categoria categoria) {
        if(dados.descricao() != null) {
            this.descricao = dados.descricao();
        }
        if(dados.valorUnt() != null) {
            this.valorUnt = dados.valorUnt();
        }
        if(dados.idCategoria() != null) {
            this.categoria = categoria;
        }
    }

    public void adicionarQtdeAoEstoque(BigDecimal qtdeConvertida) {

        this.qtdEmEstoque = this.qtdEmEstoque.add(qtdeConvertida);

    }

    public void removerQtdeDoEstoque(BigDecimal qtdeConvertida) {

        if(this.qtdEmEstoque.subtract(qtdeConvertida).compareTo(BigDecimal.ZERO) < 0){
            throw new ValidacaoException("quantidade em estoque nao pode ser negativa");
        }

        this.qtdEmEstoque = this.qtdEmEstoque.subtract(qtdeConvertida);


    }

    public void incrementarValorUnt(BigDecimal valorTotal, BigDecimal qtdeConvertida) {
        BigDecimal valorTotalDoEstoque = this.qtdEmEstoque.multiply(this.valorUnt);

        BigDecimal divisor = this.qtdEmEstoque.add(qtdeConvertida);

        this.valorUnt = valorTotalDoEstoque.add(valorTotal).divide(divisor, 4,RoundingMode.HALF_UP);


    }

    public void decrementarValorUnt(BigDecimal valorTotal, BigDecimal qtdeConvertida) {
        BigDecimal oldValorTotal = this.qtdEmEstoque.multiply(this.valorUnt);

        BigDecimal divisor = this.qtdEmEstoque.subtract(qtdeConvertida);

        if(divisor.compareTo(ZERO) == 0) {
            this.valorUnt = ZERO;
        } else {
            this.valorUnt = oldValorTotal.subtract(valorTotal).divide(divisor, 4,RoundingMode.HALF_UP);
        }
    }
}
