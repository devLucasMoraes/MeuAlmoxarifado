package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.controller.itemDeCompra.dto.request.EditItemDeCompraDTO;
import com.example.MeuAlmoxarifado.controller.itemDeCompra.dto.request.NewItemDeCompraDTO;
import com.example.MeuAlmoxarifado.controller.vinculoMaterialComFornecedora.dto.request.NewVinculoComFornecedorasDTO;
import com.example.MeuAlmoxarifado.domain.model.ItemDeCompra;
import com.example.MeuAlmoxarifado.domain.model.Material;
import com.example.MeuAlmoxarifado.domain.model.NfeDeCompra;
import com.example.MeuAlmoxarifado.domain.repository.ItemDeCompraRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemDeCompraService {

    @Autowired
    MaterialService materialService;

    @Autowired
    ItemDeCompraRepository itemDeCompraRepository;

    @Autowired
    VinculoMaterialComFornecedoraService vinculoMaterialComFornecedoraService;

    @Transactional
    public void atualizarItensTransacaoEntrada(NfeDeCompra nfeDeCompra, List<EditItemDeCompraDTO> listaAtualizadaDTO) {

        List<ItemDeCompra> itensParaRemover = new ArrayList<>();

        for (var item : nfeDeCompra.getItens()) {
            var material = item.getMaterial();

            if (!isMaterialPresent(material.getId(), listaAtualizadaDTO)) {
                itensParaRemover.add(item);
            }
        }

        nfeDeCompra.getItens().removeAll(itensParaRemover);
        itemDeCompraRepository.deleteAll(itensParaRemover);

        for (var itemAtualizado : listaAtualizadaDTO) {

            var material = materialService.getEntityById(itemAtualizado.idMaterial());

            ItemDeCompra item = nfeDeCompra.getItens().stream()
                    .filter(itemJaExistente -> itemJaExistente.getMaterial().getId().equals(itemAtualizado.idMaterial()))
                    .findFirst()
                    .orElseGet((() -> criarNovoItem(new NewItemDeCompraDTO(itemAtualizado), material, nfeDeCompra)));


            item.update(itemAtualizado, material, nfeDeCompra);



        }

    }

    @Transactional
    public void adicionarItensTransacaoEntrada(List<NewItemDeCompraDTO> listaNovosItens, NfeDeCompra nfeDeCompra) {

        for (var novoItem : listaNovosItens) {
            var material = materialService.getEntityById(novoItem.idMaterial());

            criarNovoItem(novoItem, material, nfeDeCompra);

        }

    }

    private boolean isMaterialPresent(long materialId, List<EditItemDeCompraDTO> listaAtualizadaDTO) {
        return listaAtualizadaDTO.stream()
                .anyMatch(dto -> dto.idMaterial() == materialId);
    }

    private ItemDeCompra criarNovoItem(@Valid NewItemDeCompraDTO item, Material material, NfeDeCompra nfeDeCompra) {
        ItemDeCompra novoItem =  new ItemDeCompra(
                null,
                nfeDeCompra,
                material,
                item.undCom(),
                item.quantCom(),
                item.valorUntCom(),
                item.valorIpi(),
                item.xProd(),
                item.codProd()
        );
        if(item.codProd() != null) {
            vinculoMaterialComFornecedoraService.verificarVinculo(
                    new NewVinculoComFornecedorasDTO(
                            nfeDeCompra.getFornecedora().getId(),
                            item.codProd(),
                            new ArrayList<>()
                    ),
                    material,
                    nfeDeCompra.getFornecedora()
            );
        }


        nfeDeCompra.adicionarItem(novoItem);

        return novoItem;
    }
}
