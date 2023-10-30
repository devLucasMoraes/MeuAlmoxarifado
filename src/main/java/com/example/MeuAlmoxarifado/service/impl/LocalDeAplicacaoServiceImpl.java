package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.LocalDeAplicacao;
import com.example.MeuAlmoxarifado.domain.repository.LocalDeAplicacaoRepository;
import com.example.MeuAlmoxarifado.service.LocalDeAplicacaoService;
import com.example.MeuAlmoxarifado.service.exception.BusinessException;
import com.example.MeuAlmoxarifado.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalDeAplicacaoServiceImpl implements LocalDeAplicacaoService {

    private final LocalDeAplicacaoRepository localDeAplicacaoRepository;

    public LocalDeAplicacaoServiceImpl(LocalDeAplicacaoRepository localDeAplicacaoRepository) {
        this.localDeAplicacaoRepository = localDeAplicacaoRepository;
    }

    @Transactional(readOnly = true)
    public Page<LocalDeAplicacao> findAll(Pageable pageable) {
        return this.localDeAplicacaoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public LocalDeAplicacao findById(Long id) {
        return this.localDeAplicacaoRepository.findById(id).orElseThrow(() -> new NotFoundException("Local de aplicaçao"));
    }

    @Transactional
    public LocalDeAplicacao create(LocalDeAplicacao localDeAplicacaoToCreate) {

        if(localDeAplicacaoRepository.existsByNome(localDeAplicacaoToCreate.getNome())){
            throw new BusinessException("O nome do Local de aplicaçao ja existe.");
        }

        return this.localDeAplicacaoRepository.save(localDeAplicacaoToCreate);
    }

    @Transactional
    public LocalDeAplicacao update(Long id, LocalDeAplicacao localDeAplicacaoToUpdate) {
        LocalDeAplicacao dbLocalDeAplicacao = this.findById(id);

        if(!dbLocalDeAplicacao.getId().equals(localDeAplicacaoToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        dbLocalDeAplicacao.setNome(localDeAplicacaoToUpdate.getNome());


        return this.localDeAplicacaoRepository.save(dbLocalDeAplicacao);
    }

    @Transactional
    public void delete(Long id) {
        LocalDeAplicacao dbLocalDeAplicacao = this.findById(id);
        this.localDeAplicacaoRepository.delete(dbLocalDeAplicacao);
    }

    public Boolean existsById(Long id) {
        return this.localDeAplicacaoRepository.existsById(id);
    }
}
