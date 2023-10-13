package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.Destino;
import com.example.MeuAlmoxarifado.domain.repository.DestinoRepository;
import com.example.MeuAlmoxarifado.service.DestinoService;
import com.example.MeuAlmoxarifado.service.exception.BusinessException;
import com.example.MeuAlmoxarifado.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class DestinoServiceImpl implements DestinoService {

    private final DestinoRepository destinoRepository;

    public DestinoServiceImpl(DestinoRepository destinoRepository) {
        this.destinoRepository = destinoRepository;
    }

    @Transactional(readOnly = true)
    public List<Destino> findAll() {
        return this.destinoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Destino findById(Long id) {
        return this.destinoRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Destino create(Destino destinoToCreate) {
        ofNullable(destinoToCreate).orElseThrow(() -> new BusinessException("O destino a ser criado não deve ser nulo."));
        ofNullable(destinoToCreate.getNome()).orElseThrow(() -> new BusinessException("O nome do destino não dever ser nulo"));

        if(destinoRepository.existsByNome(destinoToCreate.getNome())){
            throw new BusinessException("O nome do destino ja existe");
        }

        return this.destinoRepository.save(destinoToCreate);
    }

    @Transactional
    public Destino update(Long id, Destino destinoToUpdate) {
        Destino dbDestino = this.findById(id);

        if(!dbDestino.getId().equals(destinoToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        dbDestino.setNome(destinoToUpdate.getNome());
        dbDestino.setFone(destinoToUpdate.getFone());

        return this.destinoRepository.save(dbDestino);
    }

    @Transactional
    public void delete(Long id) {
        Destino dbDestino = this.findById(id);
        this.destinoRepository.delete(dbDestino);
    }
}
