package com.example.MeuAlmoxarifado.service;

import com.example.MeuAlmoxarifado.domain.model.BaseItem;
import com.example.MeuAlmoxarifado.domain.model.Fornecedora;
import com.example.MeuAlmoxarifado.domain.model.Material;

import java.math.BigDecimal;

public interface ConversaoDeCompraService {
    BigDecimal coverterQuantidadeParaUndidadeDeEstoque(BaseItem item, Fornecedora fornecedora, Material dbMaterial);
}
