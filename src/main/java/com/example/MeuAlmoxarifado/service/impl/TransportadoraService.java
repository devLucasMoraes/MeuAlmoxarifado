package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.controller.transportadora.dto.request.EditTransportadoraDTO;
import com.example.MeuAlmoxarifado.controller.transportadora.dto.request.NewTransportadoraDTO;
import com.example.MeuAlmoxarifado.controller.transportadora.dto.response.ListTransportadoraDTO;
import com.example.MeuAlmoxarifado.controller.transportadora.dto.response.ShowTransportadoraDTO;
import com.example.MeuAlmoxarifado.domain.model.Transportadora;
import com.example.MeuAlmoxarifado.domain.repository.TransportadoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransportadoraService {

    @Autowired
    private TransportadoraRepository transportadoraRepository;

    @Transactional
    public ShowTransportadoraDTO create(NewTransportadoraDTO dados) {
        var transportadora = new Transportadora(dados);
        transportadoraRepository.save(transportadora);
        return new ShowTransportadoraDTO(transportadora);
    }

    public Page<ListTransportadoraDTO> getAll(Pageable pageable, String nomeFantasia) {
        return transportadoraRepository.findByNomeFantasiaContainingIgnoreCase(nomeFantasia, pageable).map(transportadora -> new ListTransportadoraDTO(transportadora));
    }

    public ShowTransportadoraDTO getById(Long id) {
        var transportadora = transportadoraRepository.getReferenceById(id);
        return new ShowTransportadoraDTO(transportadora);
    }

    public ShowTransportadoraDTO getByCnpj(String cnpj) {
        var transportadora = transportadoraRepository.getReferenceByCnpj(cnpj);
        if (transportadora == null) {
            throw new ValidacaoException("CNPJ da transportadora informada não existe");
        }
        return new ShowTransportadoraDTO(transportadora);
    }

    @Transactional
    public ShowTransportadoraDTO updateById(Long id, EditTransportadoraDTO dados) {
        var transportadora = transportadoraRepository.getReferenceById(id);
        transportadora.update(dados);
        return new ShowTransportadoraDTO(transportadora);
    }

    @Transactional
    public void deleteById(Long id) {
        transportadoraRepository.deleteById(id);
    }

    public Transportadora getEntityById(Long id) {
        if (!transportadoraRepository.existsById(id)) {
            throw new ValidacaoException("Id da transportadora informado não existe");
        }
        return transportadoraRepository.getReferenceById(id);
    }
}
