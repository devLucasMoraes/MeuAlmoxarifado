package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.*;
import com.example.MeuAlmoxarifado.domain.repository.RequisicaoDeEstoqueRepository;
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
public class RequisicaoDeEstoqueServiceImpl implements RequisicaoDeEstoqueService {

    private final RequisicaoDeEstoqueRepository requisicaoDeEstoqueRepository;

    private final LocalDeAplicacaoService localDeAplicacaoService;

    private final RequisitanteService requisitanteService;

    private final MaterialService materialService;

    private final MovimentacaoService movimentacaoService;

    private final ConversaoDeConsumoService conversaoDeConsumoService;

    public RequisicaoDeEstoqueServiceImpl(RequisicaoDeEstoqueRepository requisicaoDeEstoqueRepository, LocalDeAplicacaoService localDeAplicacaoService, RequisitanteService requisitanteService, MaterialService materialService, MovimentacaoService movimentacaoService, ConversaoDeConsumoService conversaoDeConsumoService) {
        this.requisicaoDeEstoqueRepository = requisicaoDeEstoqueRepository;
        this.localDeAplicacaoService = localDeAplicacaoService;
        this.requisitanteService = requisitanteService;
        this.materialService = materialService;
        this.movimentacaoService = movimentacaoService;
        this.conversaoDeConsumoService = conversaoDeConsumoService;
    }

    @Transactional(readOnly = true)
    public Page<RequisicaoDeEstoque> findAll(Pageable pageable) {
        return this.requisicaoDeEstoqueRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public RequisicaoDeEstoque findById(Long id) {
        return this.requisicaoDeEstoqueRepository.findById(id).orElseThrow(() -> new NotFoundException("Requisiçao"));
    }

    @Transactional
    public RequisicaoDeEstoque create(RequisicaoDeEstoque requisicaoToCreate) {

        if (!this.requisitanteService.existsById(requisicaoToCreate.getRequisitante().getId())) {
            throw new NotFoundException("Requisitante");
        }

        if (!this.localDeAplicacaoService.existsById(requisicaoToCreate.getLocalDeAplicacao().getId())) {
            throw new NotFoundException("Local de aplicação");
        }

        requisicaoToCreate.getItens().forEach(itemRequisicao -> {
            if (!this.materialService.existsById(itemRequisicao.getMaterial().getId())) {
                throw new NotFoundException("Material");
            }
            itemRequisicao.setRequisicaoDeEstoque(requisicaoToCreate);


            Movimentacao saida = criarMovimentacaoSaida(itemRequisicao, "Requisição de estoque. Local id: %s , Requisitante id: %s"
                    .formatted(requisicaoToCreate.getLocalDeAplicacao().getId(), requisicaoToCreate.getRequisitante().getId()));
            this.movimentacaoService.registrarSaida(saida);

            itemRequisicao.setValorUntEnt(saida.getValorUnt());

        });

        BigDecimal valorTotalItens = requisicaoToCreate.getItens().stream().map(ItemRequisicao::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        requisicaoToCreate.setValorTotal(valorTotalItens);

        return this.requisicaoDeEstoqueRepository.save(requisicaoToCreate);
    }

    @Transactional
    public RequisicaoDeEstoque update(Long id, RequisicaoDeEstoque requisicaoToUpdate) {
        RequisicaoDeEstoque dbRequisicaoDeEstoque = this.findById(id);

        if (!dbRequisicaoDeEstoque.getId().equals(requisicaoToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        if (!this.requisitanteService.existsById(requisicaoToUpdate.getRequisitante().getId())) {
            throw new NotFoundException("Requisitante");
        }

        if (!this.localDeAplicacaoService.existsById(requisicaoToUpdate.getLocalDeAplicacao().getId())) {
            throw new NotFoundException("Local de aplicação");
        }

        dbRequisicaoDeEstoque.getItens().forEach(itemRequisicao -> {
            Movimentacao entrada = criarMovimentacaoEntrada(itemRequisicao, "Alteração de Requisiçao id : %s".formatted(dbRequisicaoDeEstoque.getId()));
            this.movimentacaoService.registrarEntrada(entrada);
        });

        requisicaoToUpdate.getItens().forEach(itemRequisicao -> {
            if (!this.materialService.existsById(itemRequisicao.getMaterial().getId())) {
                throw new NotFoundException("Material");
            }
            itemRequisicao.setRequisicaoDeEstoque(requisicaoToUpdate);

            Movimentacao saida = criarMovimentacaoSaida(itemRequisicao, "Alteração de Requisiçao id : %s"
                    .formatted(dbRequisicaoDeEstoque.getId()));
            this.movimentacaoService.registrarSaida(saida);

            itemRequisicao.setValorUntEnt(saida.getValorUnt());
        });

        BigDecimal valorTotalItens = requisicaoToUpdate.getItens().stream().map(ItemRequisicao::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        dbRequisicaoDeEstoque.setObs(requisicaoToUpdate.getObs());
        dbRequisicaoDeEstoque.setValorTotal(valorTotalItens);
        dbRequisicaoDeEstoque.setDataRequisicao(requisicaoToUpdate.getDataRequisicao());
        dbRequisicaoDeEstoque.setLocalDeAplicacao(requisicaoToUpdate.getLocalDeAplicacao());
        dbRequisicaoDeEstoque.setRequisitante(requisicaoToUpdate.getRequisitante());
        dbRequisicaoDeEstoque.setOrdemProducao(requisicaoToUpdate.getOrdemProducao());
        dbRequisicaoDeEstoque.getItens().clear();
        dbRequisicaoDeEstoque.getItens().addAll(requisicaoToUpdate.getItens());

        return this.requisicaoDeEstoqueRepository.save(dbRequisicaoDeEstoque);
    }

    @Transactional
    public void delete(Long id) {
        RequisicaoDeEstoque dbRequisicaoDeEstoque = this.findById(id);

        dbRequisicaoDeEstoque.getItens().forEach(itemRequisicao -> {
            Movimentacao entrada = criarMovimentacaoEntrada(itemRequisicao, "Cancelamento de Requisiçao id : %s".formatted(dbRequisicaoDeEstoque.getId()));
            this.movimentacaoService.registrarEntrada(entrada);
        });

        this.requisicaoDeEstoqueRepository.delete(dbRequisicaoDeEstoque);
    }

    private Movimentacao criarMovimentacaoSaida(ItemRequisicao itemRequisicao, String justificativa) {
        Material dbMaterial = this.materialService.findById(itemRequisicao.getMaterial().getId());

        BigDecimal qtdConvertida = this.conversaoDeConsumoService.coverterQuantidadeParaUndidadeDeEstoque(itemRequisicao, dbMaterial);
        BigDecimal valorTotalEntregue = dbMaterial.getValorUntMed().multiply(itemRequisicao.getQuantEntregue());

        Movimentacao saida = new Movimentacao();
        saida.setTipo(Tipo.SAIDA);
        saida.setMaterial(dbMaterial);
        saida.setData(LocalDateTime.now());
        saida.setQuantidade(qtdConvertida);
        saida.setUnidade(dbMaterial.getCategoria().getUndEstoque());
        saida.setValorUnt(dbMaterial.getValorUntMed());
        saida.setValorTotal(valorTotalEntregue);
        saida.setJustificativa(justificativa);
        return saida;
    }

    private Movimentacao criarMovimentacaoEntrada(ItemRequisicao itemRequisicao, String justificativa) {
        Material dbMaterial = this.materialService.findById(itemRequisicao.getMaterial().getId());

        BigDecimal qtdConvertida = this.conversaoDeConsumoService.coverterQuantidadeParaUndidadeDeEstoque(itemRequisicao, dbMaterial);
        BigDecimal valorTotalEntregue = itemRequisicao.getValorUntEnt().multiply(itemRequisicao.getQuantEntregue());
        BigDecimal valorUnt = valorTotalEntregue.divide(qtdConvertida,4, RoundingMode.HALF_UP);

        Movimentacao entrada = new Movimentacao();
        entrada.setTipo(Tipo.ENTRADA);
        entrada.setMaterial(dbMaterial);
        entrada.setData(LocalDateTime.now());
        entrada.setQuantidade(qtdConvertida);
        entrada.setUnidade(dbMaterial.getCategoria().getUndEstoque());
        entrada.setValorUnt(valorUnt);
        entrada.setValorTotal(valorTotalEntregue);
        entrada.setJustificativa(justificativa);
        return entrada;
    }
}
