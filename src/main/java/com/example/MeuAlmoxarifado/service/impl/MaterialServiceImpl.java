package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.Material;
import com.example.MeuAlmoxarifado.domain.repository.MaterialRepository;
import com.example.MeuAlmoxarifado.service.MaterialService;
import com.example.MeuAlmoxarifado.service.exception.BusinessException;
import com.example.MeuAlmoxarifado.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialServiceImpl(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Transactional(readOnly = true)
    public List<Material> findAll() {
        return this.materialRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Material findById(Long id) {
        return this.materialRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Material create(Material materialToCreate) {
        ofNullable(materialToCreate).orElseThrow(() -> new BusinessException("o material a ser criada não deve ser nulo."));
        ofNullable(materialToCreate.getDescricao()).orElseThrow(() -> new BusinessException("A descrição do material a ser criado não deve ser nula."));

        if(materialRepository.existsByDescricao(materialToCreate.getDescricao())){
            throw new BusinessException("Material com mesma descrição já cadastrada");
        }

        return this.materialRepository.save(materialToCreate);
    }

    @Transactional
    public Material update(Long id, Material materialToUpdate) {
        Material dbMaterial = this.findById(id);

        if(!dbMaterial.getId().equals(materialToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        dbMaterial.setDescricao(materialToUpdate.getDescricao());
        dbMaterial.setValorUnt(materialToUpdate.getValorUnt());
        dbMaterial.setQtdEmEstoque(materialToUpdate.getQtdEmEstoque());
        dbMaterial.getFornecedorasVinculadas().clear();
        dbMaterial.getFornecedorasVinculadas().addAll(materialToUpdate.getFornecedorasVinculadas());
        dbMaterial.setCategoria(materialToUpdate.getCategoria());

        return this.materialRepository.save(materialToUpdate);
    }

    @Transactional
    public void delete(Long id) {
        Material dbMaterial = this.findById(id);
        this.materialRepository.delete(dbMaterial);
    }
}
