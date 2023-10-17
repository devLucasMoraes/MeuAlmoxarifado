package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.Material;
import com.example.MeuAlmoxarifado.domain.model.Movimentacao;
import com.example.MeuAlmoxarifado.domain.model.NfeDeCompra;
import com.example.MeuAlmoxarifado.domain.model.Tipo;
import com.example.MeuAlmoxarifado.domain.repository.NfeDeCompraRepository;
import com.example.MeuAlmoxarifado.service.*;
import com.example.MeuAlmoxarifado.service.exception.BusinessException;
import com.example.MeuAlmoxarifado.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NfeDeCompraServiceImpl implements NfeDeCompraService {

    private final NfeDeCompraRepository nfeDeCompraRepository;

    private final MovimentacaoService movimentacaoService;

    private final FornecedoraService fornecedoraService;

    private final TransportadoraService transportadoraService;

    private final MaterialService materialService;

    private final ConversaoDeCompraService conversaoDeCompraService;

    public NfeDeCompraServiceImpl(NfeDeCompraRepository nfeDeCompraRepository, MovimentacaoService movimentacaoService, FornecedoraService fornecedoraService, TransportadoraService transportadoraService, MaterialService materialService, ConversaoDeCompraService conversaoDeCompraService) {
        this.nfeDeCompraRepository = nfeDeCompraRepository;
        this.movimentacaoService = movimentacaoService;
        this.fornecedoraService = fornecedoraService;
        this.transportadoraService = transportadoraService;
        this.materialService = materialService;
        this.conversaoDeCompraService = conversaoDeCompraService;
    }

    @Transactional(readOnly = true)
    public List<NfeDeCompra> findAll() {
        return this.nfeDeCompraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public NfeDeCompra findById(Long id) {
        return this.nfeDeCompraRepository.findById(id).orElseThrow(() -> new NotFoundException("N-fe"));
    }

    @Transactional
    public NfeDeCompra create(NfeDeCompra nfeDeCompraToCreate) {

        if(!this.fornecedoraService.existsById(nfeDeCompraToCreate.getFornecedora().getId())) {
            throw new NotFoundException("Fornecedora");
        }

        if(!this.transportadoraService.existsById(nfeDeCompraToCreate.getTransportadora().getId())) {
            throw new NotFoundException("Transportadora");
        }

        nfeDeCompraToCreate.getItens().forEach(itemDeCompra -> {
            if(!this.materialService.existsById(itemDeCompra.getMaterial().getId())) {
                throw new NotFoundException("Material");
            }
            itemDeCompra.setNfeDeCompra(nfeDeCompraToCreate);

            Material dbMaterial = this.materialService.findById(itemDeCompra.getMaterial().getId());
            BigDecimal qtdConvertida = this.conversaoDeCompraService.coverterQuantidadeParaUndidadeDeEstoque(itemDeCompra, nfeDeCompraToCreate.getFornecedora(), dbMaterial);
            BigDecimal valorTotalCom = itemDeCompra.getValorUntCom().multiply(itemDeCompra.getQuantCom()).add(itemDeCompra.getValorIpi());
            BigDecimal valorUnt = valorTotalCom.divide(qtdConvertida,4, RoundingMode.HALF_UP);

            Movimentacao entrada = new Movimentacao();
            entrada.setTipo(Tipo.ENTRADA);
            entrada.setMaterial(dbMaterial);
            entrada.setData(LocalDateTime.now());
            entrada.setQuantidade(qtdConvertida);
            entrada.setUnidade(dbMaterial.getCategoria().getUndEstoque());
            entrada.setValorUnt(valorUnt);
            entrada.setValorTotal(valorTotalCom);
            entrada.setJustificativa("referente a nfe");

            this.movimentacaoService.entrada(entrada);

        });


        return this.nfeDeCompraRepository.save(nfeDeCompraToCreate);
    }

    @Transactional
    public NfeDeCompra update(Long id, NfeDeCompra nfeDeCompraToUpdate) {
        NfeDeCompra dbNfeDeCompra = this.findById(id);

        if(!dbNfeDeCompra.getId().equals(nfeDeCompraToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        dbNfeDeCompra.getItens().forEach(itemDeCompra -> {
            Material dbMaterial = this.materialService.findById(itemDeCompra.getMaterial().getId());

            BigDecimal qtdConvertida = this.conversaoDeCompraService.coverterQuantidadeParaUndidadeDeEstoque(itemDeCompra, nfeDeCompraToUpdate.getFornecedora(), dbMaterial);
            BigDecimal valorTotalCom = itemDeCompra.getValorUntCom().multiply(itemDeCompra.getQuantCom()).add(itemDeCompra.getValorIpi());
            BigDecimal valorUnt = valorTotalCom.divide(qtdConvertida,4, RoundingMode.HALF_UP);

            Movimentacao saida = new Movimentacao();
            saida.setTipo(Tipo.SAIDA);
            saida.setMaterial(dbMaterial);
            saida.setData(LocalDateTime.now());
            saida.setQuantidade(qtdConvertida);
            saida.setUnidade(dbMaterial.getCategoria().getUndEstoque());
            saida.setValorUnt(valorUnt);
            saida.setValorTotal(valorTotalCom);
            saida.setJustificativa("referente a nfe");

            this.movimentacaoService.saida(saida);
        });

        nfeDeCompraToUpdate.getItens().forEach(itemDeCompra -> {
            if(!this.materialService.existsById(itemDeCompra.getMaterial().getId())) {
                throw new NotFoundException("Material");
            }
            itemDeCompra.setNfeDeCompra(nfeDeCompraToUpdate);

            Material dbMaterial = this.materialService.findById(itemDeCompra.getMaterial().getId());
            BigDecimal qtdConvertida = this.conversaoDeCompraService.coverterQuantidadeParaUndidadeDeEstoque(itemDeCompra, nfeDeCompraToUpdate.getFornecedora(), dbMaterial);
            BigDecimal valorTotalCom = itemDeCompra.getValorUntCom().multiply(itemDeCompra.getQuantCom()).add(itemDeCompra.getValorIpi());
            BigDecimal valorUnt = valorTotalCom.divide(qtdConvertida,4, RoundingMode.HALF_UP);

            Movimentacao entrada = new Movimentacao();
            entrada.setTipo(Tipo.ENTRADA);
            entrada.setMaterial(dbMaterial);
            entrada.setData(LocalDateTime.now());
            entrada.setQuantidade(qtdConvertida);
            entrada.setUnidade(dbMaterial.getCategoria().getUndEstoque());
            entrada.setValorUnt(valorUnt);
            entrada.setValorTotal(valorTotalCom);
            entrada.setJustificativa("referente a nfe");

            this.movimentacaoService.entrada(entrada);
        });

        dbNfeDeCompra.setNfe(nfeDeCompraToUpdate.getNfe());
        dbNfeDeCompra.setChaveNfe(nfeDeCompraToUpdate.getChaveNfe());
        dbNfeDeCompra.setDataEmissao(nfeDeCompraToUpdate.getDataEmissao());
        dbNfeDeCompra.setDataRecebimento(nfeDeCompraToUpdate.getDataRecebimento());
        dbNfeDeCompra.setValorTotalProdutos(nfeDeCompraToUpdate.getValorTotalProdutos());
        dbNfeDeCompra.setValorFrete(nfeDeCompraToUpdate.getValorFrete());
        dbNfeDeCompra.setValorTotalIpi(nfeDeCompraToUpdate.getValorTotalIpi());
        dbNfeDeCompra.setValorSeguro(nfeDeCompraToUpdate.getValorSeguro());
        dbNfeDeCompra.setValorDesconto(nfeDeCompraToUpdate.getValorDesconto());
        dbNfeDeCompra.setValorTotalNfe(nfeDeCompraToUpdate.getValorTotalNfe());
        dbNfeDeCompra.setValorOutros(nfeDeCompraToUpdate.getValorOutros());
        dbNfeDeCompra.setObs(nfeDeCompraToUpdate.getObs());
        dbNfeDeCompra.setTransportadora(nfeDeCompraToUpdate.getTransportadora());
        dbNfeDeCompra.setFornecedora(nfeDeCompraToUpdate.getFornecedora());
        dbNfeDeCompra.getItens().clear();
        dbNfeDeCompra.getItens().addAll(nfeDeCompraToUpdate.getItens());


        NfeDeCompra nfeDeCompraUpdated = this.nfeDeCompraRepository.save(dbNfeDeCompra);



        return nfeDeCompraUpdated;
    }

    @Transactional
    public void delete(Long id) {
        NfeDeCompra dbNfeDeCompra = this.findById(id);
        this.nfeDeCompraRepository.delete(dbNfeDeCompra);
    }
}
