package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.Requisitante;
import com.example.MeuAlmoxarifado.domain.repository.RequisitanteRepository;
import com.example.MeuAlmoxarifado.service.RequisitanteService;
import com.example.MeuAlmoxarifado.service.exception.BusinessException;
import com.example.MeuAlmoxarifado.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RequisitanteServiceImpl implements RequisitanteService {

    private final RequisitanteRepository requisitanteRepository;

    public RequisitanteServiceImpl(RequisitanteRepository requisitanteRepository) {
        this.requisitanteRepository = requisitanteRepository;
    }


   @Transactional(readOnly = true)
    public List<Requisitante> findAll() {
        return this.requisitanteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Requisitante findById(Long id) {
        return this.requisitanteRepository.findById(id).orElseThrow(() -> new NotFoundException("Requisitante"));
    }

    @Transactional
    public Requisitante create(Requisitante requisitanteToCreate) {

        if(requisitanteRepository.existsByNome(requisitanteToCreate.getNome())){
            throw new BusinessException("O nome do requisitante ja existe");
        }

        return this.requisitanteRepository.save(requisitanteToCreate);
    }

    @Transactional
    public Requisitante update(Long id, Requisitante requisitanteUpdate) {
        Requisitante dbRequisitante = this.findById(id);

        if(!dbRequisitante.getId().equals(requisitanteUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        dbRequisitante.setNome(requisitanteUpdate.getNome());
        dbRequisitante.setFone(requisitanteUpdate.getFone());

        return this.requisitanteRepository.save(dbRequisitante);
    }

    @Transactional
    public void delete(Long id) {
        Requisitante dbRequisitante = this.findById(id);

        this.requisitanteRepository.delete(dbRequisitante);
    }

    public Boolean existsById(Long id) {
       return this.requisitanteRepository.existsById(id);
    }
}
