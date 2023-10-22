package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.ItemRequisicao;
import com.example.MeuAlmoxarifado.domain.model.Material;
import com.example.MeuAlmoxarifado.service.ConversaoDeConsumoService;
import com.example.MeuAlmoxarifado.service.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ConversaoDeConsumoServiceImpl implements ConversaoDeConsumoService {
    public BigDecimal coverterQuantidadeParaUndidadeDeEstoque(ItemRequisicao item, Material dbMaterial) {
        if (item.getUndConsumo() == dbMaterial.getCategoria().getUndEstoque()) {
            return item.getQuantEntregue();
        }

        var conversaoDeConsumo = dbMaterial.getCategoria().getConversoesDeConsumo().stream()
                .filter(conversao -> conversao.getUndConsumo().equals(item.getUndConsumo()))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Conversão de %s para %s não encontrada".formatted(item.getUndConsumo(), dbMaterial.getCategoria().getUndEstoque())));

        return item.getQuantEntregue().multiply(conversaoDeConsumo.getFatorDeConversao());
    }
}
