package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.*;
import com.example.MeuAlmoxarifado.domain.repository.NfeDeCompraRepository;
import com.example.MeuAlmoxarifado.service.*;
import com.example.MeuAlmoxarifado.service.exception.BusinessException;
import com.example.MeuAlmoxarifado.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

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
    public Page<NfeDeCompra> findAll(Pageable pageable) {
        return this.nfeDeCompraRepository.findAll(pageable);
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

            Movimentacao entrada = criarMovimentacaoEntrada(itemDeCompra, nfeDeCompraToCreate, "Entrada de NFe %s".formatted(nfeDeCompraToCreate.getNfe()));

            this.movimentacaoService.registrarEntradaAoEstoqueFisico(entrada);

        });


        return this.nfeDeCompraRepository.save(nfeDeCompraToCreate);
    }

    @Transactional
    public NfeDeCompra update(Long id, NfeDeCompra nfeDeCompraToUpdate) {
        NfeDeCompra dbNfeDeCompra = this.findById(id);

        if(!dbNfeDeCompra.getId().equals(nfeDeCompraToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        if(!this.fornecedoraService.existsById(nfeDeCompraToUpdate.getFornecedora().getId())) {
            throw new NotFoundException("Fornecedora");
        }

        if(!this.transportadoraService.existsById(nfeDeCompraToUpdate.getTransportadora().getId())) {
            throw new NotFoundException("Transportadora");
        }

        dbNfeDeCompra.getItens().forEach(itemDeCompra -> {
            Movimentacao saida = criarMovimentacaoSaida(itemDeCompra, nfeDeCompraToUpdate, "Alteração de NFe id : %s".formatted(dbNfeDeCompra.getId()));
            this.movimentacaoService.registrarSaidaDoEstoqueFisico(saida);
        });

        nfeDeCompraToUpdate.getItens().forEach(itemDeCompra -> {
            if(!this.materialService.existsById(itemDeCompra.getMaterial().getId())) {
                throw new NotFoundException("Material");
            }
            itemDeCompra.setNfeDeCompra(nfeDeCompraToUpdate);
            Movimentacao entrada = criarMovimentacaoEntrada(itemDeCompra, nfeDeCompraToUpdate, "Alteração de NFe id : %s".formatted(dbNfeDeCompra.getId()));

            this.movimentacaoService.registrarEntradaAoEstoqueFisico(entrada);
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


        return this.nfeDeCompraRepository.save(dbNfeDeCompra);
    }

    @Transactional
    public void delete(Long id) {
        NfeDeCompra dbNfeDeCompra = this.findById(id);
        dbNfeDeCompra.getItens().forEach(itemDeCompra -> {
            Movimentacao saida = criarMovimentacaoSaida(itemDeCompra, dbNfeDeCompra, "Cancelamento de NFe id : %s".formatted(dbNfeDeCompra.getId()));
            this.movimentacaoService.registrarSaidaDoEstoqueFisico(saida);
        });

        this.nfeDeCompraRepository.delete(dbNfeDeCompra);
    }

    private Movimentacao criarMovimentacaoEntrada(ItemDeCompra itemDeCompra, NfeDeCompra nfe, String justificativa) {
        Material dbMaterial = this.materialService.findById(itemDeCompra.getMaterial().getId());
        BigDecimal qtdConvertida = this.conversaoDeCompraService.coverterQuantidadeParaUndidadeDeEstoque(itemDeCompra, nfe.getFornecedora(), dbMaterial);
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
        entrada.setJustificativa(justificativa);
        return entrada;
    }

    private Movimentacao criarMovimentacaoSaida(ItemDeCompra itemDeCompra, NfeDeCompra nfe, String justificativa) {
        Material dbMaterial = this.materialService.findById(itemDeCompra.getMaterial().getId());

        BigDecimal qtdConvertida = this.conversaoDeCompraService.coverterQuantidadeParaUndidadeDeEstoque(itemDeCompra, nfe.getFornecedora(), dbMaterial);
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
        saida.setJustificativa(justificativa);
        return saida;
    }

}
