package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.*;
import com.example.MeuAlmoxarifado.service.ConversaoDeCompraService;
import com.example.MeuAlmoxarifado.service.ConversaoDeConsumoService;
import com.example.MeuAlmoxarifado.service.MaterialService;
import com.example.MeuAlmoxarifado.service.MovimentacaoFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class MovimentacaoFactoryImpl implements MovimentacaoFactory {

    private final MaterialService materialService;

    private final ConversaoDeCompraService conversaoDeCompraService;

    private final ConversaoDeConsumoService conversaoDeConsumoService;

    public MovimentacaoFactoryImpl(MaterialService materialService, ConversaoDeCompraService conversaoDeCompraService, ConversaoDeConsumoService conversaoDeConsumoService) {
        this.materialService = materialService;
        this.conversaoDeCompraService = conversaoDeCompraService;
        this.conversaoDeConsumoService = conversaoDeConsumoService;
    }

    public Movimentacao criarMovimentacaoEntrada(ItemDeCompra itemDeCompra, NfeDeCompra nfe, String justificativa) {
        Material dbMaterial = materialService.findById(itemDeCompra.getMaterial().getId());

        BigDecimal qtdConvertida = getQtdConvertida(itemDeCompra, dbMaterial, nfe.getFornecedora());

        BigDecimal valorTotal = getValorTotal(itemDeCompra);

        BigDecimal valorUnitario = getValorUnitario(valorTotal, qtdConvertida);

        Movimentacao entrada = criarMovimentacaoBase(dbMaterial, Tipo.ENTRADA,
                qtdConvertida, valorUnitario,
                valorTotal, justificativa);

        return entrada;
    }

    public Movimentacao criarMovimentacaoSaida(ItemDeCompra itemDeCompra, NfeDeCompra nfe, String justificativa) {
        Material dbMaterial = materialService.findById(itemDeCompra.getMaterial().getId());

        BigDecimal qtdConvertida = getQtdConvertida(itemDeCompra, dbMaterial, nfe.getFornecedora());

        BigDecimal valorTotal = getValorTotal(itemDeCompra);

        BigDecimal valorUnitario = getValorUnitario(valorTotal, qtdConvertida);

        Movimentacao saida = criarMovimentacaoBase(dbMaterial, Tipo.SAIDA,
                qtdConvertida, valorUnitario,
                valorTotal, justificativa);

        return saida;
    }

    public Movimentacao criarMovimentacaoSaida(ItemRequisicao itemRequisicao, String justificativa) {
        Material dbMaterial = this.materialService.findById(itemRequisicao.getMaterial().getId());

        BigDecimal qtdConvertida = getQtdConvertida(itemRequisicao, dbMaterial);

        BigDecimal valorTotal = getValorTotal(itemRequisicao, dbMaterial);

        BigDecimal valorUnitario = dbMaterial.getValorUntMed();

        Movimentacao saida = criarMovimentacaoBase(dbMaterial, Tipo.SAIDA,
                qtdConvertida, valorUnitario,
                valorTotal, justificativa);

        return saida;
    }

    public Movimentacao criarMovimentacaoEntrada(ItemRequisicao itemRequisicao, String justificativa) {
        Material dbMaterial = this.materialService.findById(itemRequisicao.getMaterial().getId());

        BigDecimal qtdConvertida = getQtdConvertida(itemRequisicao, dbMaterial);

        BigDecimal valorTotal = getValorTotal(itemRequisicao, dbMaterial);

        BigDecimal valorUnitario = getValorUnitario(valorTotal, qtdConvertida);

        Movimentacao entrada = criarMovimentacaoBase(dbMaterial, Tipo.ENTRADA,
                qtdConvertida, valorUnitario,
                valorTotal, justificativa);

        return entrada;
    }

    public Movimentacao criarMovimentacaoSaida(ItemEmprestimoETroca itemEmprestimoETroca, String justificativa) {
        Material dbMaterial = this.materialService.findById(itemEmprestimoETroca.getMaterial().getId());

        BigDecimal valorTotal = getValorTotal(itemEmprestimoETroca, dbMaterial);

        BigDecimal valorUnitario = dbMaterial.getValorUntMed();

        Movimentacao saida = criarMovimentacaoBase(dbMaterial, Tipo.SAIDA,
                itemEmprestimoETroca.getQuantidade(), valorUnitario,
                valorTotal, justificativa);

        return saida;

    }

    public Movimentacao criarMovimentacaoEntrada(ItemEmprestimoETroca itemEmprestimoETroca, String justificativa) {
        Material dbMaterial = this.materialService.findById(itemEmprestimoETroca.getMaterial().getId());

        Movimentacao entrada = criarMovimentacaoBase(dbMaterial, Tipo.ENTRADA,
                itemEmprestimoETroca.getQuantidade(), BigDecimal.ZERO,
                BigDecimal.ZERO, justificativa);

        return entrada;
    }

    private Movimentacao criarMovimentacaoBase(Material material, Tipo tipo,
                                               BigDecimal qtd, BigDecimal valorUnit,
                                               BigDecimal valorTotal, String justificativa) {

        Movimentacao mov = new Movimentacao();

        mov.setMaterial(material);
        mov.setTipo(tipo);
        mov.setData(LocalDateTime.now());
        mov.setQuantidade(qtd);
        mov.setUnidade(material.getCategoria().getUndEstoque());
        mov.setValorUnt(valorUnit);
        mov.setValorTotal(valorTotal);
        mov.setJustificativa(justificativa);

        return mov;
    }

    private BigDecimal getQtdConvertida(ItemDeCompra item, Material material, Fornecedora fornecedora) {
        return conversaoDeCompraService.coverterQuantidadeParaUndidadeDeEstoque(item, fornecedora, material);
    }

    private BigDecimal getQtdConvertida(ItemRequisicao item, Material material) {
        return conversaoDeConsumoService.coverterQuantidadeParaUndidadeDeEstoque(item, material);
    }

    private BigDecimal getValorTotal(ItemDeCompra itemDeCompra) {
        return itemDeCompra.getValorUnitario().multiply(itemDeCompra.getQuantidade()).add(itemDeCompra.getValorIpi());
    }

    private BigDecimal getValorTotal(BaseItem item, Material dbMaterial) {
        return dbMaterial.getValorUntMed().multiply(item.getQuantidade());
    }

    private BigDecimal getValorUnitario(BigDecimal valorTotal, BigDecimal qtd) {
        return valorTotal.divide(qtd, 4, RoundingMode.HALF_UP);
    }


}
