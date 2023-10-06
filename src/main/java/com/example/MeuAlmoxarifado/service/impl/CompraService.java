package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.controller.compra.dto.request.EditCompraDTO;
import com.example.MeuAlmoxarifado.controller.compra.dto.request.NewCompraDTO;
import com.example.MeuAlmoxarifado.controller.compra.dto.response.ListCompraDTO;
import com.example.MeuAlmoxarifado.controller.compra.dto.response.ShowCompraDTO;
import com.example.MeuAlmoxarifado.domain.model.NfeDeCompra;
import com.example.MeuAlmoxarifado.domain.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private TransportadoraService transportadoraService;

    @Autowired
    private FornecedoraService fornecedoraService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ItemDeCompraService itemDeCompraService;

    @Transactional
    public ShowCompraDTO create(NewCompraDTO dados) {

        var transportadora = transportadoraService.getEntityById(dados.idTransportadora());
        var fornecedora = fornecedoraService.getEntityById(dados.idFornecedora());

        var CompraComNFe = new NfeDeCompra(
                null,
                dados.nfe(),
                dados.chaveNfe(),
                dados.dataEmissao(),
                dados.dataRecebimento(),
                dados.valorTotalProdutos(),
                dados.valorFrete(),
                dados.valorTotalIpi(),
                dados.valorSeguro(),
                dados.valorDesconto(),
                dados.valorTotalNfe(),
                dados.valorOutros(),
                dados.obs(),
                transportadora,
                fornecedora,
                new ArrayList<>()
        );

        itemDeCompraService.adicionarItensTransacaoEntrada(dados.itens(), CompraComNFe);

        materialService.adcionarAoEstoque(CompraComNFe);

        var valorTotal = CompraComNFe.getItens().stream().map(ItemDeCompra::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        var valorIpiTotal = CompraComNFe.getItens().stream().map(ItemDeCompra::getValorIpi).reduce(BigDecimal.ZERO, BigDecimal::add);
        CompraComNFe.setValorTotal(valorTotal);
        CompraComNFe.setValorIpiTotal(valorIpiTotal);

        compraRepository.save(CompraComNFe);

        return new ShowCompraDTO(CompraComNFe);
    }

    public Page<ListCompraDTO> getAll(Pageable pageable) {
        return compraRepository.findAll(pageable).map(transacaoEntrada -> new ListCompraDTO(transacaoEntrada));
    }

    public ShowCompraDTO getById(Long id) {
        var transacaoEntrada = compraRepository.getReferenceById(id);
        return new ShowCompraDTO(transacaoEntrada);
    }

    @Transactional
    public ShowCompraDTO updateById(Long id, EditCompraDTO dadosAtualizados) {
        var transacaoEntrada = compraRepository.getReferenceById(id);

        materialService.deletarDoEstoque(transacaoEntrada);


        itemDeCompraService.atualizarItensTransacaoEntrada(transacaoEntrada, dadosAtualizados.itens());

        transacaoEntrada.update(
                dadosAtualizados,
                transportadoraService.getEntityById(dadosAtualizados.idTransportadora()),
                fornecedoraService.getEntityById(dadosAtualizados.idFornecedora())
        );

        materialService.adcionarAoEstoque(transacaoEntrada);

        var valorTotal = transacaoEntrada.getItens().stream().map(ItemDeCompra::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        var valorIpiTotal = transacaoEntrada.getItens().stream().map(ItemDeCompra::getValorIpi).reduce(BigDecimal.ZERO, BigDecimal::add);
        transacaoEntrada.setValorTotal(valorTotal);
        transacaoEntrada.setValorIpiTotal(valorIpiTotal);

        compraRepository.save(transacaoEntrada);

        return new ShowCompraDTO(transacaoEntrada);
    }

    @Transactional
    public void deleteById(Long id) {
        var transacaoEntrada = compraRepository.getReferenceById(id);

        materialService.deletarDoEstoque(transacaoEntrada);

        compraRepository.deleteById(id);
    }
}
