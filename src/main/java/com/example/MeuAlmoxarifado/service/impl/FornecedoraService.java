package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.controller.fornecedora.dto.request.EditFornecedoraDTO;
import com.example.MeuAlmoxarifado.controller.fornecedora.dto.request.NewFornecedoraDTO;
import com.example.MeuAlmoxarifado.controller.fornecedora.dto.response.ListFornecedoraDTO;
import com.example.MeuAlmoxarifado.controller.fornecedora.dto.response.ShowFornecedoraDTO;
import com.example.MeuAlmoxarifado.domain.model.Fornecedora;
import com.example.MeuAlmoxarifado.domain.repository.FornecedoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FornecedoraService {

    @Autowired
    private FornecedoraRepository fornecedoraRepository;

    @Transactional
    public ShowFornecedoraDTO create(NewFornecedoraDTO dados) {
        var fornecedora = new Fornecedora(dados);
        fornecedoraRepository.save(fornecedora);
        return new ShowFornecedoraDTO(fornecedora);
    }

    public Page<ListFornecedoraDTO> getAll(Pageable pageable, String nomeFantasia) {
        return fornecedoraRepository.findByNomeFantasiaContainingIgnoreCase(nomeFantasia, pageable).map(ListFornecedoraDTO::new);
    }

    public ShowFornecedoraDTO getById(Long id) {
        var fornecedora = fornecedoraRepository.getReferenceById(id);
        return new ShowFornecedoraDTO(fornecedora);
    }

    public ShowFornecedoraDTO getByCnpj(String cnpj) {
        var fornecedora = fornecedoraRepository.getReferenceByCnpj(cnpj);
        if (fornecedora == null) {
            throw new ValidacaoException("CNPJ da fornecedora informada não existe");
        }
        return new ShowFornecedoraDTO(fornecedora);
    }

    @Transactional
    public ShowFornecedoraDTO updateById(Long id, EditFornecedoraDTO dados) {
        var fornecedora = fornecedoraRepository.getReferenceById(id);
        fornecedora.update(dados);
        return new ShowFornecedoraDTO(fornecedora);
    }

    @Transactional
    public void deleteById(Long id) {
        fornecedoraRepository.deleteById(id);
    }

    public Fornecedora getEntityById(Long id) {
        if (!fornecedoraRepository.existsById(id)) {
            throw new ValidacaoException("Id da fornecedora informada não existe");
        }
        return fornecedoraRepository.getReferenceById(id);
    }


}
