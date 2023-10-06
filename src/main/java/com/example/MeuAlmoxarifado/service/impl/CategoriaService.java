package com.example.MeuAlmoxarifado.service.impl;


import com.example.MeuAlmoxarifado.controller.categoria.dto.request.EditCategoriaDTO;
import com.example.MeuAlmoxarifado.controller.categoria.dto.request.NewCategoriaDTO;
import com.example.MeuAlmoxarifado.controller.categoria.dto.response.ListCategoriaDTO;
import com.example.MeuAlmoxarifado.controller.categoria.dto.response.ShowCategoriaDTO;
import com.example.MeuAlmoxarifado.domain.model.Categoria;
import com.example.MeuAlmoxarifado.domain.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ConversaoDeConsumoService conversaoDeConsumoService;

    @Transactional
    public ShowCategoriaDTO create(NewCategoriaDTO dados) {
        var categoria = new Categoria(dados);

        if (dados.conversoesDeConsumo() != null && !dados.conversoesDeConsumo().isEmpty()) {
            conversaoDeConsumoService.adicionarConversoesDeConsumo(dados.conversoesDeConsumo(), categoria);
        }

        categoriaRepository.save(categoria);

        return new ShowCategoriaDTO(categoria);
    }

    public Page<ListCategoriaDTO> getAll(Pageable pageable, String nome) {

        return categoriaRepository.findByNomeContainingIgnoreCase(nome, pageable).map(ListCategoriaDTO::new);

    }

    public ShowCategoriaDTO getById(Long id) {

        var categoria = categoriaRepository.getReferenceById(id);

        return new ShowCategoriaDTO(categoria);

    }

    @Transactional
    public ShowCategoriaDTO updateById(Long id, EditCategoriaDTO dados) {

        var categoria = categoriaRepository.getReferenceById(id);

        categoria.update(dados);

        if (dados.conversoesDeConsumo() == null || dados.conversoesDeConsumo().isEmpty())  {
            categoria.getConversoesDeConsumo().clear();
        }

        if (dados.conversoesDeConsumo() != null) {
            conversaoDeConsumoService.atualizarConversoesDeConsumo(dados.conversoesDeConsumo(), categoria);
        }


        return new ShowCategoriaDTO(categoria);

    }

    @Transactional
    public void deleteById(Long id) {
        categoriaRepository.deleteById(id);
    }

    public Categoria getEntityById(Long id) {

        if(!categoriaRepository.existsById(id)) {
            throw new ValidacaoException("Id da categoria informada não existe");
        }

        return categoriaRepository.getReferenceById(id);

    }
}
