package com.example.MeuAlmoxarifado.service;

import com.example.MeuAlmoxarifado.domain.model.Movimentacao;

public interface MovimentacaoService {

    void entrada(Movimentacao entrada);

    void saida(Movimentacao saida);

}
