package com.example.MeuAlmoxarifado.controller.dto.material.response;



import com.example.MeuAlmoxarifado.controller.dto.conversaoDeCompra.response.ShowConversaoDeCompraDTO;
import com.example.MeuAlmoxarifado.controller.dto.vinculoMaterialComFornecedora.response.ShowVinculoComFornecedorasDTO;
import com.example.MeuAlmoxarifado.domain.model.Material;
import com.example.MeuAlmoxarifado.domain.model.VinculoMaterialComFornecedora;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public record ShowMaterialDTO(
        Long id,

        String descricao,

        BigDecimal valorUnt,

        BigDecimal qtdEmEstoque,

        Long idCategoria,

        List<ShowVinculoComFornecedorasDTO> fornecedorasVinculadas
) {
    public ShowMaterialDTO(Material material) {
        this(material.getId(), material.getDescricao(), material.getValorUntMed(), material.getQtdEmEstoque(), material.getCategoria().getId(), toDTO(material.getFornecedorasVinculadas()));
    }

    private static List<ShowVinculoComFornecedorasDTO> toDTO(List<VinculoMaterialComFornecedora> vinculo) {
        return vinculo.stream().map(item -> new ShowVinculoComFornecedorasDTO(
                item.getId(),
                item.getFornecedora().getId(),
                item.getMaterial().getId(),
                item.getCodProd(),
                item.getConversaoDeCompras().stream().map(ShowConversaoDeCompraDTO::new)
                        .collect(Collectors.toList())
        )).collect(Collectors.toList());
    }
}
