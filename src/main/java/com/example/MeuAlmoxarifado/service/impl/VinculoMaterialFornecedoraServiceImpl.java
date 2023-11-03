package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.VinculoMaterialFornecedora;
import com.example.MeuAlmoxarifado.domain.repository.VinculoMaterialFornecedoraRepository;
import com.example.MeuAlmoxarifado.service.FornecedoraService;
import com.example.MeuAlmoxarifado.service.MaterialService;
import com.example.MeuAlmoxarifado.service.VinculoMaterialFornecedoraService;
import com.example.MeuAlmoxarifado.service.exception.BusinessException;
import com.example.MeuAlmoxarifado.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Optional.ofNullable;

@Service
public class VinculoMaterialFornecedoraServiceImpl implements VinculoMaterialFornecedoraService {

    private final VinculoMaterialFornecedoraRepository vinculoMaterialFornecedoraRepository;

    private final FornecedoraService fornecedoraService;

    private final MaterialService materialService;

    public VinculoMaterialFornecedoraServiceImpl(VinculoMaterialFornecedoraRepository vinculoMaterialFornecedoraRepository, FornecedoraService fornecedoraService, MaterialService materialService) {
        this.vinculoMaterialFornecedoraRepository = vinculoMaterialFornecedoraRepository;
        this.fornecedoraService = fornecedoraService;
        this.materialService = materialService;
    }

    @Transactional(readOnly = true)
    public Page<VinculoMaterialFornecedora> findAll(Pageable pageable) {
        return this.vinculoMaterialFornecedoraRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public VinculoMaterialFornecedora findById(Long id) {
        return this.vinculoMaterialFornecedoraRepository.findById(id).orElseThrow(() -> new NotFoundException("Vinculo"));
    }

    @Transactional
    public VinculoMaterialFornecedora create(VinculoMaterialFornecedora vinculoToCreate) {
        ofNullable(vinculoToCreate.getMaterial().getId()).orElseThrow(() -> new BusinessException("O campo Material não deve ser nulo."));

        if (!this.fornecedoraService.existsById(vinculoToCreate.getFornecedora().getId())) {
            throw new NotFoundException("Fornecedora");
        }

        if (!this.materialService.existsById(vinculoToCreate.getMaterial().getId())) {
            throw new NotFoundException("Material");
        }

        vinculoToCreate.getConversaoDeCompras().forEach(conversaoDeCompra -> conversaoDeCompra.setVinculoComFornecedoras(vinculoToCreate));


        return this.vinculoMaterialFornecedoraRepository.save(vinculoToCreate);
    }

    @Transactional
    public VinculoMaterialFornecedora update(Long id, VinculoMaterialFornecedora vinculoToUpdate) {
        VinculoMaterialFornecedora dbVinculo = this.findById(id);

        if (!dbVinculo.getId().equals(vinculoToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        if (!this.fornecedoraService.existsById(vinculoToUpdate.getFornecedora().getId())) {
            throw new NotFoundException("Fornecedora");
        }

        if (!this.materialService.existsById(vinculoToUpdate.getMaterial().getId())) {
            throw new NotFoundException("Material");
        }

        vinculoToUpdate.getConversaoDeCompras().forEach(conversaoDeCompra -> conversaoDeCompra.setVinculoComFornecedoras(vinculoToUpdate));

        dbVinculo.setFornecedora(vinculoToUpdate.getFornecedora());
        dbVinculo.setMaterial(vinculoToUpdate.getMaterial());
        dbVinculo.setReferenciaFornecedora(vinculoToUpdate.getReferenciaFornecedora());
        dbVinculo.setDescricaoFornecedora(vinculoToUpdate.getDescricaoFornecedora());
        dbVinculo.getConversaoDeCompras().clear();
        dbVinculo.getConversaoDeCompras().addAll(vinculoToUpdate.getConversaoDeCompras());

        return this.vinculoMaterialFornecedoraRepository.save(dbVinculo);
    }

    @Transactional
    public void delete(Long id) {
        VinculoMaterialFornecedora dbVinculo = this.findById(id);
        var material = dbVinculo.getMaterial();
        material.removerVinculo(dbVinculo);
        this.vinculoMaterialFornecedoraRepository.delete(dbVinculo);
    }

    public Boolean existsById(Long id) {
        return this.vinculoMaterialFornecedoraRepository.existsById(id);
    }
    @Transactional(readOnly = true)
    public Page<VinculoMaterialFornecedora> dynamicFindAll(Specification<VinculoMaterialFornecedora> spec, Pageable pageable) {
        return this.vinculoMaterialFornecedoraRepository.findAll(spec, pageable);
    }
}
