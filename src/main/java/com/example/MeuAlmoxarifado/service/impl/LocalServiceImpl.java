package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.Local;
import com.example.MeuAlmoxarifado.domain.repository.LocalRepository;
import com.example.MeuAlmoxarifado.service.LocalService;
import com.example.MeuAlmoxarifado.service.exception.BusinessException;
import com.example.MeuAlmoxarifado.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocalServiceImpl implements LocalService {

    private final LocalRepository localRepository;

    public LocalServiceImpl(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    @Transactional(readOnly = true)
    public List<Local> findAll() {
        return this.localRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Local findById(Long id) {
        return this.localRepository.findById(id).orElseThrow(() -> new NotFoundException("Local"));
    }

    @Transactional
    public Local create(Local localToCreate) {

        if(localRepository.existsByNome(localToCreate.getNome())){
            throw new BusinessException("O nome do Local ja existe");
        }

        return this.localRepository.save(localToCreate);
    }

    @Transactional
    public Local update(Long id, Local localToUpdate) {
        Local dbLocal = this.findById(id);

        if(!dbLocal.getId().equals(localToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        dbLocal.setNome(localToUpdate.getNome());


        return this.localRepository.save(dbLocal);
    }

    @Transactional
    public void delete(Long id) {
        Local dbLocal = this.findById(id);
        this.localRepository.delete(dbLocal);
    }
}
