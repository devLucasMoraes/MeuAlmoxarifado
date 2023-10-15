package com.example.MeuAlmoxarifado.service;

import com.example.MeuAlmoxarifado.domain.model.Fornecedora;
import com.example.MeuAlmoxarifado.domain.model.ItemDeCompra;
import com.example.MeuAlmoxarifado.domain.model.Material;

import java.math.BigDecimal;

public interface ConversaoDeCompraService {
    BigDecimal coverterQuantidadeParaUndidadeDeEstoque(ItemDeCompra item, Fornecedora fornecedora, Material dbMaterial);
}
