package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.*;
import com.example.MeuAlmoxarifado.domain.repository.RequisicaoDeEstoqueRepository;
import com.example.MeuAlmoxarifado.service.*;
import com.example.MeuAlmoxarifado.service.exception.BusinessException;
import com.example.MeuAlmoxarifado.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    public List<RequisicaoDeEstoque> findAll() {
        return this.requisicaoDeEstoqueRepository.findAll();
    }

    @Transactional(readOnly = true)
    public RequisicaoDeEstoque findById(Long id) {
        return this.requisicaoDeEstoqueRepository.findById(id).orElseThrow(() -> new NotFoundException("Requisiçao"));
    }

    @Transactional
    public RequisicaoDeEstoque create(RequisicaoDeEstoque requisicaoDeEstoqueToCreate) {
        if (!this.requisitanteService.existsById(requisicaoDeEstoqueToCreate.getRequisitante().getId())) {
            throw new NotFoundException("Requisitante");
        }

        if (!this.localDeAplicacaoService.existsById(requisicaoDeEstoqueToCreate.getLocalDeAplicacao().getId())) {
            throw new NotFoundException("Local de aplicação");
        }

        requisicaoDeEstoqueToCreate.getItens().forEach(itemRequisicao -> {
            if (!this.materialService.existsById(itemRequisicao.getMaterial().getId())) {
                throw new NotFoundException("Material");
            }
            itemRequisicao.setRequisicaoDeEstoque(requisicaoDeEstoqueToCreate);

            Movimentacao saida = criarMovimentacaoSaida(itemRequisicao, "Requisição de estoque. Local id: %s , Requisitante id: %s"
                    .formatted(requisicaoDeEstoqueToCreate.getLocalDeAplicacao().getId(), requisicaoDeEstoqueToCreate.getRequisitante().getId()));
            this.movimentacaoService.registrarSaida(saida);

        });

        return this.requisicaoDeEstoqueRepository.save(requisicaoDeEstoqueToCreate);
    }

    @Transactional
    public RequisicaoDeEstoque update(Long id, RequisicaoDeEstoque requisicaoDeEstoqueToUpdate) {
        RequisicaoDeEstoque dbRequisicaoDeEstoque = this.findById(id);

        if (!dbRequisicaoDeEstoque.getId().equals(requisicaoDeEstoqueToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        if (!this.requisitanteService.existsById(requisicaoDeEstoqueToUpdate.getRequisitante().getId())) {
            throw new NotFoundException("Requisitante");
        }

        if (!this.localDeAplicacaoService.existsById(requisicaoDeEstoqueToUpdate.getLocalDeAplicacao().getId())) {
            throw new NotFoundException("Local de aplicação");
        }

        requisicaoDeEstoqueToUpdate.getItens().forEach(itemRequisicao -> {
            if (!this.materialService.existsById(itemRequisicao.getMaterial().getId())) {
                throw new NotFoundException("Material");
            }
            itemRequisicao.setRequisicaoDeEstoque(requisicaoDeEstoqueToUpdate);


        });

        dbRequisicaoDeEstoque.setObs(requisicaoDeEstoqueToUpdate.getObs());
        dbRequisicaoDeEstoque.setValorTotal(requisicaoDeEstoqueToUpdate.getValorTotal());
        dbRequisicaoDeEstoque.setDataRequisicao(requisicaoDeEstoqueToUpdate.getDataRequisicao());
        dbRequisicaoDeEstoque.setLocalDeAplicacao(requisicaoDeEstoqueToUpdate.getLocalDeAplicacao());
        dbRequisicaoDeEstoque.setRequisitante(requisicaoDeEstoqueToUpdate.getRequisitante());
        dbRequisicaoDeEstoque.setOrdemProducao(requisicaoDeEstoqueToUpdate.getOrdemProducao());
        dbRequisicaoDeEstoque.getItens().clear();
        dbRequisicaoDeEstoque.getItens().addAll(requisicaoDeEstoqueToUpdate.getItens());

        return this.requisicaoDeEstoqueRepository.save(dbRequisicaoDeEstoque);
    }

    @Transactional
    public void delete(Long id) {
        RequisicaoDeEstoque dbRequisicaoDeEstoque = this.findById(id);
        this.requisicaoDeEstoqueRepository.delete(dbRequisicaoDeEstoque);
    }

    private Movimentacao criarMovimentacaoSaida(ItemRequisicao itemRequisicao, String justificativa) {
        Material dbMaterial = this.materialService.findById(itemRequisicao.getMaterial().getId());

        BigDecimal qtdConvertida = this.conversaoDeConsumoService.coverterQuantidadeParaUndidadeDeEstoque(itemRequisicao, dbMaterial);
        BigDecimal valorTotalCom = dbMaterial.getValorUntMed().multiply(itemRequisicao.getQuantEntregue());

        Movimentacao saida = new Movimentacao();
        saida.setTipo(Tipo.SAIDA);
        saida.setMaterial(dbMaterial);
        saida.setData(LocalDateTime.now());
        saida.setQuantidade(qtdConvertida);
        saida.setUnidade(dbMaterial.getCategoria().getUndEstoque());
        saida.setValorUnt(dbMaterial.getValorUntMed());
        saida.setValorTotal(valorTotalCom);
        saida.setJustificativa(justificativa);
        return saida;
    }
}
