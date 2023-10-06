package com.example.MeuAlmoxarifado.service.impl;


import com.example.MeuAlmoxarifado.controller.requisitante.dto.request.EditRequisitanteDTO;
import com.example.MeuAlmoxarifado.controller.requisitante.dto.request.NewRequisitanteDTO;
import com.example.MeuAlmoxarifado.controller.requisitante.dto.response.ListRequisitanteDTO;
import com.example.MeuAlmoxarifado.controller.requisitante.dto.response.ShowRequisitanteDTO;
import com.example.MeuAlmoxarifado.domain.model.Requisitante;
import com.example.MeuAlmoxarifado.domain.repository.RequisitanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequisitanteService {

    @Autowired
    private RequisitanteRepository requisitanteRepository;

    @Transactional
    public ShowRequisitanteDTO create(NewRequisitanteDTO dados) {
        var requisitante = new Requisitante(dados);
        requisitanteRepository.save(requisitante);
        return new ShowRequisitanteDTO(requisitante);
    }

    public Page<ListRequisitanteDTO> getAll(Pageable pageable, String nome) {
        return requisitanteRepository.findByNomeContainingIgnoreCase(nome, pageable).map(requisitante -> new ListRequisitanteDTO(requisitante));
    }

    public ShowRequisitanteDTO getById(Long id) {
        var requisitante = requisitanteRepository.getReferenceById(id);
        return new ShowRequisitanteDTO(requisitante);
    }

    @Transactional
    public ShowRequisitanteDTO updateById(Long id, EditRequisitanteDTO dados) {
        var requisitante = requisitanteRepository.getReferenceById(id);
        requisitante.update(dados);
        return new ShowRequisitanteDTO(requisitante);
    }

    @Transactional
    public void deleteById(Long id) {
        requisitanteRepository.deleteById(id);
    }

    public Requisitante getEntityById(Long id) {
        if (!requisitanteRepository.existsById(id)) {
            throw new ValidacaoException("Id do requisitante informado não existe");
        }
        return requisitanteRepository.getReferenceById(id);
    }
}
