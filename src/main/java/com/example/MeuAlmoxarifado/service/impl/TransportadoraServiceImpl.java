package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.Transportadora;
import com.example.MeuAlmoxarifado.domain.repository.TransportadoraRepository;
import com.example.MeuAlmoxarifado.service.TransportadoraService;
import com.example.MeuAlmoxarifado.service.exception.BusinessException;
import com.example.MeuAlmoxarifado.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class TransportadoraServiceImpl implements TransportadoraService {

    private final TransportadoraRepository transportadoraRepository;

    public TransportadoraServiceImpl(TransportadoraRepository transportadoraRepository) {
        this.transportadoraRepository = transportadoraRepository;
    }

    @Transactional(readOnly = true)
    public List<Transportadora> findAll() {
        return this.transportadoraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Transportadora findById(Long id) {
        return this.transportadoraRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Transportadora create(Transportadora transportadoraToCreate) {
        ofNullable(transportadoraToCreate).orElseThrow(() -> new BusinessException("A transportadora a ser criado não deve ser nula."));
        ofNullable(transportadoraToCreate.getCnpj()).orElseThrow(() -> new BusinessException("O CNPJ da transportadora não deve ser nulo."));
        ofNullable(transportadoraToCreate.getRazaoSocial()).orElseThrow(() -> new BusinessException("A razão social da transportadora não deve ser nula."));

        if(transportadoraRepository.existsByCnpj(transportadoraToCreate.getCnpj())){
            throw new BusinessException("Transportadora com mesmo CNPJ já cadastrada");
        }

        return this.transportadoraRepository.save(transportadoraToCreate);
    }

    @Transactional
    public Transportadora update(Long id, Transportadora transportadoraToUpdate) {
        Transportadora dbTransportadora = this.findById(id);

        if(!dbTransportadora.getId().equals(transportadoraToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        dbTransportadora.setNomeFantasia(transportadoraToUpdate.getNomeFantasia());
        dbTransportadora.setRazaoSocial(transportadoraToUpdate.getRazaoSocial());
        dbTransportadora.setCnpj(transportadoraToUpdate.getCnpj());
        dbTransportadora.setFone(transportadoraToUpdate.getFone());

        return this.transportadoraRepository.save(dbTransportadora);
    }

    @Transactional
    public void delete(Long id) {
        Transportadora dbTransportadora = this.findById(id);
        this.transportadoraRepository.delete(dbTransportadora);
    }
}
