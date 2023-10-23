package com.example.MeuAlmoxarifado.service;

import com.example.MeuAlmoxarifado.domain.model.ItemRequisicao;
import com.example.MeuAlmoxarifado.domain.model.Material;

import java.math.BigDecimal;

public interface ConversaoDeConsumoService {
    BigDecimal coverterQuantidadeParaUndidadeDeEstoque(ItemRequisicao item, Material dbMaterial);
}
