package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.controller.destino.dto.request.EditDestinoDTO;
import com.example.MeuAlmoxarifado.controller.destino.dto.request.NewDestinoDTO;
import com.example.MeuAlmoxarifado.controller.destino.dto.response.ListDestinoDTO;
import com.example.MeuAlmoxarifado.controller.destino.dto.response.ShowDestinoDTO;
import com.example.MeuAlmoxarifado.domain.model.Destino;
import com.example.MeuAlmoxarifado.domain.repository.DestinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DestinoService {

    @Autowired
    private DestinoRepository destinoRepository;

    @Transactional
    public ShowDestinoDTO create(NewDestinoDTO dados) {
        var destino = new Destino(dados);
        destinoRepository.save(destino);
        return new ShowDestinoDTO(destino);
    }

    public Page<ListDestinoDTO> getAll(Pageable pageable, String nome) {
        return destinoRepository.findByNomeContainingIgnoreCase(nome, pageable).map(destino -> new ListDestinoDTO(destino));
    }

    public ShowDestinoDTO getById(Long id) {
        var destino = destinoRepository.getReferenceById(id);
        return new ShowDestinoDTO(destino);
    }

    @Transactional
    public ShowDestinoDTO updateById(Long id, EditDestinoDTO dados) {
        var destino = destinoRepository.getReferenceById(id);
        destino.update(dados);
        return new ShowDestinoDTO(destino);
    }

    @Transactional
    public void deleteById(Long id) {
        destinoRepository.deleteById(id);
    }

    public Destino getEntityById(Long id) {
        if (!destinoRepository.existsById(id)) {
            throw new ValidacaoException("Id do destino informado não existe");
        }
        return destinoRepository.getReferenceById(id);
    }
}
