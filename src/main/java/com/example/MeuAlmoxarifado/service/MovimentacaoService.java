package com.example.MeuAlmoxarifado.service;

import com.example.MeuAlmoxarifado.domain.model.Movimentacao;

public interface MovimentacaoService {

    void registrarEntradaAoEstoqueFisico(Movimentacao entrada);

    void registrarSaidaAoEstoqueFisico(Movimentacao saida);

}
