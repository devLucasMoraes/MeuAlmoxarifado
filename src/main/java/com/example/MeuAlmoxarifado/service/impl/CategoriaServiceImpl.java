package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.Categoria;
import com.example.MeuAlmoxarifado.domain.repository.CategoriaRepository;
import com.example.MeuAlmoxarifado.service.CategoriaService;
import com.example.MeuAlmoxarifado.service.exception.BusinessException;
import com.example.MeuAlmoxarifado.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<Categoria> findAll() {
        return this.categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Categoria findById(Long id) {
        return this.categoriaRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Categoria create(Categoria categoriaToCreate) {
        ofNullable(categoriaToCreate).orElseThrow(() -> new BusinessException("A categoria a ser criada não deve ser nula."));
        ofNullable(categoriaToCreate.getNome()).orElseThrow(() -> new BusinessException("O nome da categoria a ser criado não deve ser nula."));

        if(categoriaRepository.existsByNome(categoriaToCreate.getNome())){
            throw new BusinessException("Categoria com mesmo nome já cadastrada");
        }

        if(!categoriaToCreate.getConversoesDeConsumo().isEmpty()){
            categoriaToCreate.getConversoesDeConsumo().forEach(conversao -> conversao.setCategoria(categoriaToCreate));
        }

        return this.categoriaRepository.save(categoriaToCreate);
    }

    @Transactional
    public Categoria update(Long id, Categoria categoriaToUpdate) {
        Categoria dbCategoria = this.findById(id);

        if(!dbCategoria.getId().equals(categoriaToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        if(!categoriaToUpdate.getConversoesDeConsumo().isEmpty()){
            categoriaToUpdate.getConversoesDeConsumo().forEach(conversao -> conversao.setCategoria(dbCategoria));
        }

        dbCategoria.setNome(categoriaToUpdate.getNome());
        dbCategoria.setUndEstoque(categoriaToUpdate.getUndEstoque());
        dbCategoria.setEstoqueMinimo(categoriaToUpdate.getEstoqueMinimo());
        dbCategoria.getConversoesDeConsumo().clear();
        dbCategoria.getConversoesDeConsumo().addAll(categoriaToUpdate.getConversoesDeConsumo());

        return this.categoriaRepository.save(dbCategoria);
    }

    @Transactional
    public void delete(Long id) {
        Categoria dbCategoria = this.findById(id);
        this.categoriaRepository.delete(dbCategoria);
    }
}
