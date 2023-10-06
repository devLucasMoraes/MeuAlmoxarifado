package com.example.MeuAlmoxarifado.service.impl;


import com.example.MeuAlmoxarifado.controller.transacaoSaida.dto.request.EditTransacaoSaidaDTO;
import com.example.MeuAlmoxarifado.controller.transacaoSaida.dto.request.NewTransacaoSaidaDTO;
import com.example.MeuAlmoxarifado.controller.transacaoSaida.dto.response.ListTransacaoSaidaDTO;
import com.example.MeuAlmoxarifado.controller.transacaoSaida.dto.response.ShowTransacaoSaidaDTO;
import com.example.MeuAlmoxarifado.domain.model.TransacaoSaida;
import com.example.MeuAlmoxarifado.domain.repository.TransacaoSaidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class TransacaoSaidaService {

    @Autowired
    private TransacaoSaidaRepository transacaoSaidaRepository;

    @Autowired
    private RequisitanteService requisitanteService;

    @Autowired
    private DestinoService destinoService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ItemTransacaoSaidaService itemTransacaoSaidaService;

    @Transactional
    public ShowTransacaoSaidaDTO create(NewTransacaoSaidaDTO dados) {

        var requisitante = requisitanteService.getEntityById(dados.idRequisitante());
        var destino = destinoService.getEntityById(dados.idDestino());

        var transacaoSaida = new TransacaoSaida(
                null,
                dados.dataRequisicao(),
                new BigDecimal(0),
                dados.obs(),
                dados.ordemProducao(),
                requisitante,
                destino,
                new ArrayList<>()
        );

        itemTransacaoSaidaService.adicionarItensTransacaoSaida(dados.itens(), transacaoSaida);

        materialService.adcionarAoEstoque(transacaoSaida);

        var valorTotal = transacaoSaida.getItens().stream().map(ItemTransacaoSaida::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        transacaoSaida.setValorTotal(valorTotal);
        transacaoSaidaRepository.save(transacaoSaida);

        return new ShowTransacaoSaidaDTO(transacaoSaida);
    }

    public Page<ListTransacaoSaidaDTO> getAll(Pageable pageable) {
        return transacaoSaidaRepository.findAll(pageable).map(transacaoEntrada -> new ListTransacaoSaidaDTO(transacaoEntrada));
    }

    public ShowTransacaoSaidaDTO getById(Long id) {
        var transacaoSaida = transacaoSaidaRepository.getReferenceById(id);
        return new ShowTransacaoSaidaDTO(transacaoSaida);
    }

    @Transactional
    public ShowTransacaoSaidaDTO updateById(Long id, EditTransacaoSaidaDTO dadosAtualizados) {
        var transacaoSaida = transacaoSaidaRepository.getReferenceById(id);

        materialService.deletarDoEstoque(transacaoSaida);

        itemTransacaoSaidaService.atualizarItensTransacaoSaida(transacaoSaida, dadosAtualizados.itens());

        transacaoSaida.update(
                dadosAtualizados,
                requisitanteService.getEntityById(dadosAtualizados.idRequisitante()),
                destinoService.getEntityById(dadosAtualizados.idDestino())
        );

        materialService.adcionarAoEstoque(transacaoSaida);

        var valorTotal = transacaoSaida.getItens().stream().map(ItemTransacaoSaida::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        transacaoSaida.setValorTotal(valorTotal);

        transacaoSaidaRepository.save(transacaoSaida);

        return new ShowTransacaoSaidaDTO(transacaoSaida);
    }

    @Transactional
    public void deleteById(Long id) {
        var transacaoSaida = transacaoSaidaRepository.getReferenceById(id);

        materialService.deletarDoEstoque(transacaoSaida);

        transacaoSaidaRepository.deleteById(id);
    }
}
