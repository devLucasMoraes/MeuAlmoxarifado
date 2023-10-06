package com.example.MeuAlmoxarifado.domain.model;


import com.example.MeuAlmoxarifado.controller.itemDeCompra.dto.request.EditItemDeCompraDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "itens_transacoes_entrada")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemDeCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transacoes_entrada_id")
    private NfeDeCompra nfeDeCompra;

    @ManyToOne
    @JoinColumn(name = "materiais_id")
    private Material material;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "und_com")
    private Unidade undCom;

    @JoinColumn(name = "quant_com")
    private BigDecimal quantCom;

    @JoinColumn(name = "valor_unt_com")
    private BigDecimal valorUntCom;

    @JoinColumn(name = "valor_ipi")
    private BigDecimal valorIpi;

    @JoinColumn(name = "descricao_fornecedor")
    private String descricaoFornecedor;

    @JoinColumn(name = "referencia_fornecedor")
    private String referenciaFornecedor;

    public void update(EditItemDeCompraDTO itemAtualizado, Material material, NfeDeCompra nfeDeCompra) {
        this.material = material;
        this.nfeDeCompra = nfeDeCompra;
        if(itemAtualizado.undCom() != null) {
            this.undCom = itemAtualizado.undCom();
        }
        if(itemAtualizado.quantCom() != null) {
            this.quantCom = itemAtualizado.quantCom();
        }
        if(itemAtualizado.valorUntCom() != null) {
            this.valorUntCom = itemAtualizado.valorUntCom();
        }
        if(itemAtualizado.valorIpi() != null) {
            this.valorIpi = itemAtualizado.valorIpi();
        }

    }

    public BigDecimal getQuantEstoque() {

        if (undCom == material.getCategoria().getUndEstoque()) {
            return this.quantCom;
        } else {
            VinculoMaterialComFornecedora vinculoMaterialComFornecedora = material.getFornecedorasVinculadas().stream()
                    .filter(vinculo -> vinculo.getFornecedora().equals(this.nfeDeCompra.getFornecedora()))
                    .findFirst()
                    .orElseThrow(() -> new ValidacaoException(material.getDescricao() + " precisa estar vinculada a uma Fornecedora "));

            ConversaoDeCompra conversaoDeCompra = vinculoMaterialComFornecedora.getConversaoDeCompras().stream()
                    .filter(conversao -> conversao.getUndCompra().equals(undCom))
                    .findFirst()
                    .orElseThrow(() -> new ValidacaoException("Conversão de " + undCom + " para " + material.getCategoria().getUndEstoque() + " não encontrada"));


            return this.quantCom.multiply(conversaoDeCompra.getFatorDeConversao());

        }
    }
    public BigDecimal getValorTotal() {
        return this.valorUntCom.multiply(this.quantCom).add(this.valorIpi);
    }
}
