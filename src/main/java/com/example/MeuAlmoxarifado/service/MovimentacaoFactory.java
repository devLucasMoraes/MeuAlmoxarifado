package com.example.MeuAlmoxarifado.service;

import com.example.MeuAlmoxarifado.domain.model.*;

public interface MovimentacaoFactory {

    Movimentacao criarMovimentacaoEntrada(ItemDeCompra itemDeCompra, NfeDeCompra nfe, String justificativa);

    Movimentacao criarMovimentacaoSaida(ItemDeCompra itemDeCompra, NfeDeCompra nfe, String justificativa);

    Movimentacao criarMovimentacaoSaida(ItemRequisicao itemRequisicao, String justificativa);

    Movimentacao criarMovimentacaoEntrada(ItemRequisicao itemRequisicao, String justificativa);

    Movimentacao criarMovimentacaoSaida(ItemEmprestimoETroca itemEmprestimoETroca, String justificativa);

    Movimentacao criarMovimentacaoEntrada(ItemEmprestimoETroca itemEmprestimoETroca, String justificativa);
}
