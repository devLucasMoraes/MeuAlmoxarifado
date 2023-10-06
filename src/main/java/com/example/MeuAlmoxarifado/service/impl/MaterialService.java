package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.controller.material.dto.request.EditMaterialDTO;
import com.example.MeuAlmoxarifado.controller.material.dto.request.NewMaterialDTO;
import com.example.MeuAlmoxarifado.controller.material.dto.response.ListMateriaisDTO;
import com.example.MeuAlmoxarifado.controller.material.dto.response.ShowMaterialDTO;
import com.example.MeuAlmoxarifado.domain.model.*;
import com.example.MeuAlmoxarifado.domain.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private VinculoMaterialComFornecedoraService vinculoComFornecedorasService;

    @Transactional
    public ShowMaterialDTO create(NewMaterialDTO dados) {

        var categoria = categoriaService.getEntityById(dados.idCategoria());

        var material = new Material(dados, categoria);

        if (dados.fornecedorasVinculadas() != null && !dados.fornecedorasVinculadas().isEmpty()) {
            vinculoComFornecedorasService.adicionarVinculoComFornecedoras(dados.fornecedorasVinculadas(), material);
        }

        materialRepository.save(material);

        return new ShowMaterialDTO(material);
    }

    public Page<ListMateriaisDTO> getAll(Pageable pageable, String descricao) {
        return materialRepository.findByDescricaoContainingIgnoreCase(descricao, pageable).map(ListMateriaisDTO::new);
    }

    public ShowMaterialDTO getById(Long id) {
        var material = materialRepository.getReferenceById(id);
        return new ShowMaterialDTO(material);
    }

    public ShowMaterialDTO getByCodProd(String codProd) {
        var materialId = vinculoComFornecedorasService.getMaterialByCodProd(codProd);
        var material = materialRepository.getReferenceById(materialId);
        return new ShowMaterialDTO(material);
    }

    @Transactional
    public ShowMaterialDTO updateById(Long id, EditMaterialDTO dados) {
        var material = materialRepository.getReferenceById(id);

        var categoria = categoriaService.getEntityById((dados.idCategoria()));

        if (dados.fornecedorasVinculadas() == null || dados.fornecedorasVinculadas().isEmpty())  {
            material.getFornecedorasVinculadas().clear();
        }

        if (dados.fornecedorasVinculadas() != null) {
            vinculoComFornecedorasService.atualizarVinculoComFornecedoras(dados.fornecedorasVinculadas(), material);
        }

        material.update(
                dados,
                categoria
        );

        materialRepository.save(material);

        return new ShowMaterialDTO(material);
    }

    @Transactional
    public void deleteById(Long id) {
        materialRepository.deleteById(id);
    }

    public Material getEntityById(Long id) {
        if (!materialRepository.existsById(id)) {
            throw new ValidacaoException("Id do material informado não existe");
        }
        return materialRepository.getReferenceById(id);
    }

    @Transactional
    public void adcionarAoEstoque(NfeDeCompra nfeDeCompra) {

        for (ItemDeCompra item : nfeDeCompra.getItens()) {
            Material material = item.getMaterial();

            BigDecimal qtdeConvertida = item.getQuantEstoque();
            BigDecimal valorTotal = item.getValorTotal();

            material.incrementarValorUnt(valorTotal, qtdeConvertida);
            material.adicionarQtdeAoEstoque(qtdeConvertida);

            materialRepository.save(material);
        }
    }

    @Transactional
    public void adcionarAoEstoque(TransacaoSaida transacaoSaida) {

        for (ItemTransacaoSaida item : transacaoSaida.getItens()) {
            Material material = item.getMaterial();

            BigDecimal QtdeConvertida = item.getQuantEstoque();

            material.removerQtdeDoEstoque(QtdeConvertida);

            materialRepository.save(material);
        }
    }

    @Transactional
    public void deletarDoEstoque(NfeDeCompra nfeDeCompra) {

        for (var item: nfeDeCompra.getItens()) {
            Material material = item.getMaterial();

            BigDecimal qtdeConvertida = item.getQuantEstoque();
            BigDecimal valorTotal = item.getValorTotal();
            material.decrementarValorUnt(valorTotal, qtdeConvertida);
            material.removerQtdeDoEstoque(qtdeConvertida);

            materialRepository.save(material);

        }

    }

    @Transactional
    public void deletarDoEstoque(TransacaoSaida transacaoSaida) {

        for (var item: transacaoSaida.getItens()) {
            Material material = item.getMaterial();

            BigDecimal qtdeConvertida = item.getQuantEstoque();

            BigDecimal valorTotal = item.getValorUntEnt().multiply(item.getQuantEnt());

            material.incrementarValorUnt(valorTotal, qtdeConvertida);
            material.adicionarQtdeAoEstoque(qtdeConvertida);

            materialRepository.save(material);

        }

    }

}
