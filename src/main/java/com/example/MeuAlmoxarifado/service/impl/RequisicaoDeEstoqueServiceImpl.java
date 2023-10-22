package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.RequisicaoDeEstoque;
import com.example.MeuAlmoxarifado.domain.repository.RequisicaoDeEstoqueRepository;
import com.example.MeuAlmoxarifado.service.RequisicaoDeEstoqueService;
import com.example.MeuAlmoxarifado.service.exception.BusinessException;
import com.example.MeuAlmoxarifado.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RequisicaoDeEstoqueServiceImpl implements RequisicaoDeEstoqueService {

    private final RequisicaoDeEstoqueRepository requisicaoDeEstoqueRepository;

    public RequisicaoDeEstoqueServiceImpl(RequisicaoDeEstoqueRepository requisicaoDeEstoqueRepository) {
        this.requisicaoDeEstoqueRepository = requisicaoDeEstoqueRepository;
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

        return this.requisicaoDeEstoqueRepository.save(requisicaoDeEstoqueToCreate);
    }

    @Transactional
    public RequisicaoDeEstoque update(Long id, RequisicaoDeEstoque requisicaoDeEstoqueToUpdate) {
        RequisicaoDeEstoque dbRequisicaoDeEstoque = this.findById(id);

        if(!dbRequisicaoDeEstoque.getId().equals(requisicaoDeEstoqueToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        dbRequisicaoDeEstoque.setItens(requisicaoDeEstoqueToUpdate.getItens());
        dbRequisicaoDeEstoque.setObs(requisicaoDeEstoqueToUpdate.getObs());
        dbRequisicaoDeEstoque.setValorTotal(requisicaoDeEstoqueToUpdate.getValorTotal());
        dbRequisicaoDeEstoque.setDataRequisicao(requisicaoDeEstoqueToUpdate.getDataRequisicao());
        dbRequisicaoDeEstoque.setLocalDeAplicacao(requisicaoDeEstoqueToUpdate.getLocalDeAplicacao());
        dbRequisicaoDeEstoque.setRequisitante(requisicaoDeEstoqueToUpdate.getRequisitante());
        dbRequisicaoDeEstoque.setOrdemProducao(requisicaoDeEstoqueToUpdate.getOrdemProducao());

        return this.requisicaoDeEstoqueRepository.save(dbRequisicaoDeEstoque);
    }

    @Transactional
    public void delete(Long id) {
        RequisicaoDeEstoque dbRequisicaoDeEstoque = this.findById(id);
        this.requisicaoDeEstoqueRepository.delete(dbRequisicaoDeEstoque);
    }
}
