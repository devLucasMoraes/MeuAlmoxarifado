package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.controller.vinculoMaterialComFornecedora.dto.request.EditVinculoComFornecedorasDTO;
import com.example.MeuAlmoxarifado.controller.vinculoMaterialComFornecedora.dto.request.NewVinculoComFornecedorasDTO;
import com.example.MeuAlmoxarifado.domain.model.Fornecedora;
import com.example.MeuAlmoxarifado.domain.model.Material;
import com.example.MeuAlmoxarifado.domain.model.VinculoMaterialComFornecedora;
import com.example.MeuAlmoxarifado.domain.repository.VinculoMaterialComFornecedoraRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class VinculoMaterialComFornecedoraService {

    @Autowired
    private FornecedoraService fornecedoraService;

    @Autowired
    private ConversaoDeCompraService conversaoDeCompraService;

    @Autowired
    private VinculoMaterialComFornecedoraRepository vinculoComFornecedorasRepository;

    @Transactional
    public void atualizarVinculoComFornecedoras(List<EditVinculoComFornecedorasDTO> listaAtualizadaDTO, Material material) {

        List<VinculoMaterialComFornecedora> itensParaRemover = new ArrayList<>();

        for (var item : material.getFornecedorasVinculadas()) {
            var fornecedora = item.getFornecedora();

            if (!isFornecedoraPresent(fornecedora.getId(), listaAtualizadaDTO)) {
                itensParaRemover.add(item);
            }
        }

        material.getFornecedorasVinculadas().removeAll(itensParaRemover);
        vinculoComFornecedorasRepository.deleteAll(itensParaRemover);


        for (var vinculoAtualizado : listaAtualizadaDTO) {

            var fornecedora = fornecedoraService.getEntityById(vinculoAtualizado.idFornecedora());

            VinculoMaterialComFornecedora vinculo = material.getFornecedorasVinculadas().stream()
                    .filter(item -> item.getFornecedora().getId().equals(vinculoAtualizado.idFornecedora()))
                    .findFirst()
                    .orElseGet(() -> criarNovoVinculo(new NewVinculoComFornecedorasDTO(vinculoAtualizado), material, fornecedora));

            vinculo.update(vinculoAtualizado, fornecedora, material);

            conversaoDeCompraService.atualizarConversoesDeCompra(vinculoAtualizado.conversoesDeCompra(), vinculo);

        }

    }

    @Transactional
    public void adicionarVinculoComFornecedoras(List<NewVinculoComFornecedorasDTO> listaNovosVinculos, Material material) {

        for (var novoVinculo : listaNovosVinculos) {
            var fornecedora = fornecedoraService.getEntityById(novoVinculo.idFornecedora());

            var vinculo = criarNovoVinculo(novoVinculo,material,fornecedora);

            conversaoDeCompraService.adicionarConversoesDeCompra(novoVinculo.conversoesDeCompra(), vinculo);
        }

    }

    public Long getMaterialByCodProd(String codProd) {
        var vinculo = vinculoComFornecedorasRepository.getReferenceByCodProd(codProd);
        if (vinculo == null) {
            throw new ValidacaoException("Vinculo não encontrado");
        }
        return vinculo.getMaterial().getId();
    }

    public VinculoMaterialComFornecedora criarNovoVinculo(@Valid NewVinculoComFornecedorasDTO newVinculo, Material material, Fornecedora fornecedora) {

        VinculoMaterialComFornecedora novoVinculo =  new VinculoMaterialComFornecedora(
                null,
                newVinculo.codProd(),
                material,
                fornecedora,
                new ArrayList<>()
        );

        material.adicionarVinculo(novoVinculo);

        return novoVinculo;
    }

    public void verificarVinculo(@Valid NewVinculoComFornecedorasDTO newVinculo, Material material, Fornecedora fornecedora) {

        List<VinculoMaterialComFornecedora> vinculos = vinculoComFornecedorasRepository.findAllByCodProd(newVinculo.codProd());
        var vinculoExistente = vinculos.stream()
                .filter(v -> v.getFornecedora().equals(fornecedora))
                .findFirst();

        if (vinculoExistente.isPresent()) {
            System.out.println("passou pelo isPresent()");
            var vinculo = vinculoExistente.get();

            var materialAserRemovidoVinculo = vinculo.getMaterial();

            if (!materialAserRemovidoVinculo.equals(material)) {
                System.out.println("passou pelo materialAserRemovidoVinculo");

                System.out.println(materialAserRemovidoVinculo.getDescricao());
                System.out.println(material.getDescricao());

                materialAserRemovidoVinculo.removerVinculo(vinculo);
                vinculoComFornecedorasRepository.delete(vinculo);
            }

        }
    }
    private boolean isFornecedoraPresent(long fornecedoraId, List<EditVinculoComFornecedorasDTO> listaAtualizadaDTO) {
        return listaAtualizadaDTO.stream()
                .anyMatch(dto -> dto.idFornecedora() == fornecedoraId);
    }
}
