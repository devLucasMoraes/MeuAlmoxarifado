package com.example.MeuAlmoxarifado.service;

import com.example.MeuAlmoxarifado.domain.model.BaseItem;
import com.example.MeuAlmoxarifado.domain.model.Material;

import java.math.BigDecimal;

public interface ConversaoDeConsumoService {
    BigDecimal coverterQuantidadeParaUndidadeDeEstoque(BaseItem item, Material dbMaterial);
}
