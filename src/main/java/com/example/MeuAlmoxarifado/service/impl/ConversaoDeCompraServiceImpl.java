package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.BaseItem;
import com.example.MeuAlmoxarifado.domain.model.Fornecedora;
import com.example.MeuAlmoxarifado.domain.model.Material;
import com.example.MeuAlmoxarifado.service.ConversaoDeCompraService;
import com.example.MeuAlmoxarifado.service.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ConversaoDeCompraServiceImpl implements ConversaoDeCompraService {

    public BigDecimal coverterQuantidadeParaUndidadeDeEstoque(BaseItem item, Fornecedora fornecedora, Material dbMaterial) {

        if (item.getUnidade() == dbMaterial.getCategoria().getUndEstoque()) {
            return item.getQuantidade();
        }
        var vinculoMaterialComFornecedora = dbMaterial.getFornecedorasVinculadas().stream()
                .filter(vinculo -> vinculo.getFornecedora().equals(fornecedora))
                .findFirst()
                .orElseThrow(() -> new BusinessException("%s precisa estar vinculada a uma Fornecedora.".formatted(dbMaterial.getDescricao())));

        var conversaoDeCompra = vinculoMaterialComFornecedora.getConversaoDeCompras().stream()
                .filter(conversao -> conversao.getUndCompra().equals(item.getUnidade()))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Conversão de %s para %s não encontrada".formatted(item.getUnidade(), dbMaterial.getCategoria().getUndEstoque())));


        return item.getQuantidade().multiply(conversaoDeCompra.getFatorDeConversao());
    }
}
