package com.example.MeuAlmoxarifado.service;

import com.example.MeuAlmoxarifado.domain.model.Movimentacao;

public interface MovimentacaoService {

    void registrarEntrada(Movimentacao entrada);

    void registrarSaida(Movimentacao saida);

}
