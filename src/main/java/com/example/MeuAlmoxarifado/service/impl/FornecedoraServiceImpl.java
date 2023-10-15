package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.Fornecedora;
import com.example.MeuAlmoxarifado.domain.repository.FornecedoraRepository;
import com.example.MeuAlmoxarifado.service.FornecedoraService;
import com.example.MeuAlmoxarifado.service.exception.BusinessException;
import com.example.MeuAlmoxarifado.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class FornecedoraServiceImpl implements FornecedoraService {

    private final FornecedoraRepository fornecedoraRepository;

    public FornecedoraServiceImpl(FornecedoraRepository fornecedoraRepository) {
        this.fornecedoraRepository = fornecedoraRepository;
    }

    @Transactional(readOnly = true)
    public List<Fornecedora> findAll() {
        return this.fornecedoraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Fornecedora findById(Long id) {
        return this.fornecedoraRepository.findById(id).orElseThrow(() -> new NotFoundException("Fornecedora"));
    }

    @Transactional
    public Fornecedora create(Fornecedora fornecedoraToCreate) {
        ofNullable(fornecedoraToCreate).orElseThrow(() -> new BusinessException("A fornecedora a ser criado não deve ser nula."));
        ofNullable(fornecedoraToCreate.getCnpj()).orElseThrow(() -> new BusinessException("O CNPJ da fornecedora não deve ser nulo."));
        ofNullable(fornecedoraToCreate.getRazaoSocial()).orElseThrow(() -> new BusinessException("A razão social da fornecedora não deve ser nula."));

        if(fornecedoraRepository.existsByCnpj(fornecedoraToCreate.getCnpj())){
            throw new BusinessException("Transportadora com mesmo CNPJ já cadastrada");
        }

        return this.fornecedoraRepository.save(fornecedoraToCreate);
    }

    @Transactional
    public Fornecedora update(Long id, Fornecedora fornecedoraToUpdate) {
        Fornecedora dbFornecedora = this.findById(id);

        if(!dbFornecedora.getId().equals(fornecedoraToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        dbFornecedora.setNomeFantasia(fornecedoraToUpdate.getNomeFantasia());
        dbFornecedora.setRazaoSocial(fornecedoraToUpdate.getRazaoSocial());
        dbFornecedora.setCnpj(fornecedoraToUpdate.getCnpj());
        dbFornecedora.setFone(fornecedoraToUpdate.getFone());

        return this.fornecedoraRepository.save(fornecedoraToUpdate);
    }

    @Transactional
    public void delete(Long id) {
        Fornecedora dbFornecedora = this.findById(id);
        this.fornecedoraRepository.delete(dbFornecedora);
    }

    public Boolean existsById(Long id) {
        return this.fornecedoraRepository.existsById(id);
    }
}
