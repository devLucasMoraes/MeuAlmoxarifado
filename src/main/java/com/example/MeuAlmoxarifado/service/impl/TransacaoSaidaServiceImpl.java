package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.TransacaoSaida;
import com.example.MeuAlmoxarifado.domain.repository.TransacaoSaidaRepository;
import com.example.MeuAlmoxarifado.service.TransacaoSaidaService;
import com.example.MeuAlmoxarifado.service.exception.BusinessException;
import com.example.MeuAlmoxarifado.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class TransacaoSaidaServiceImpl implements TransacaoSaidaService {

    private final TransacaoSaidaRepository transacaoSaidaRepository;

    public TransacaoSaidaServiceImpl(TransacaoSaidaRepository transacaoSaidaRepository) {
        this.transacaoSaidaRepository = transacaoSaidaRepository;
    }

    @Transactional(readOnly = true)
    public List<TransacaoSaida> findAll() {
        return this.transacaoSaidaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public TransacaoSaida findById(Long id) {
        return this.transacaoSaidaRepository.findById(id).orElseThrow(() -> new NotFoundException("Saida"));
    }

    @Transactional
    public TransacaoSaida create(TransacaoSaida transacaoSaidaToCreate) {
        ofNullable(transacaoSaidaToCreate).orElseThrow(() -> new BusinessException("A transação a ser criada não deve ser nula."));


        return this.transacaoSaidaRepository.save(transacaoSaidaToCreate);
    }

    @Transactional
    public TransacaoSaida update(Long id, TransacaoSaida transacaoSaidaToUpdate) {
        TransacaoSaida dbTransacaoSaida = this.findById(id);

        if(!dbTransacaoSaida.getId().equals(transacaoSaidaToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        dbTransacaoSaida.setItens(transacaoSaidaToUpdate.getItens());
        dbTransacaoSaida.setObs(transacaoSaidaToUpdate.getObs());
        dbTransacaoSaida.setValorTotal(transacaoSaidaToUpdate.getValorTotal());
        dbTransacaoSaida.setDataRequisicao(transacaoSaidaToUpdate.getDataRequisicao());
        dbTransacaoSaida.setDestino(transacaoSaidaToUpdate.getDestino());
        dbTransacaoSaida.setRequisitante(transacaoSaidaToUpdate.getRequisitante());
        dbTransacaoSaida.setOrdemProducao(transacaoSaidaToUpdate.getOrdemProducao());

        return this.transacaoSaidaRepository.save(dbTransacaoSaida);
    }

    @Transactional
    public void delete(Long id) {
        TransacaoSaida dbTransacaoSaida = this.findById(id);
        this.transacaoSaidaRepository.delete(dbTransacaoSaida);
    }
}
