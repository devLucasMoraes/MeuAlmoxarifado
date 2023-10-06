package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.controller.itemTransacoesSaida.dto.request.EditItemTransacaoSaidaDTO;
import com.example.MeuAlmoxarifado.controller.itemTransacoesSaida.dto.request.NewItemTransacaoSaidaDTO;
import com.example.MeuAlmoxarifado.domain.model.ItemTransacaoSaida;
import com.example.MeuAlmoxarifado.domain.model.Material;
import com.example.MeuAlmoxarifado.domain.model.TransacaoSaida;
import com.example.MeuAlmoxarifado.domain.repository.ItemTransacaoSaidaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemTransacaoSaidaService {

    @Autowired
    MaterialService materialService;

    @Autowired
    ItemTransacaoSaidaRepository itemTransacaoSaidaRepository;

    @Transactional
    public void atualizarItensTransacaoSaida(TransacaoSaida transacaoSaida, List<EditItemTransacaoSaidaDTO> listaAtualizadaDTO) {

        List<ItemTransacaoSaida> itensParaRemover = new ArrayList<>();

        for (var item : transacaoSaida.getItens()) {
            var material = item.getMaterial();

            if (!isMaterialPresent(material.getId(), listaAtualizadaDTO)) {
                itensParaRemover.add(item);
            }
        }

        transacaoSaida.getItens().removeAll(itensParaRemover);
        itemTransacaoSaidaRepository.deleteAll(itensParaRemover);

        for (var itemAtualizado : listaAtualizadaDTO) {

            var material = materialService.getEntityById(itemAtualizado.idMaterial());

            ItemTransacaoSaida item = transacaoSaida.getItens().stream()
                    .filter(itemJaExistente -> itemJaExistente.getMaterial().getId().equals(itemAtualizado.idMaterial()))
                    .findFirst()
                    .orElseGet((() -> criarNovoItem(new NewItemTransacaoSaidaDTO(itemAtualizado), material, transacaoSaida)));

            item.update(itemAtualizado, material, transacaoSaida);


        }

    }

    @Transactional
    public void adicionarItensTransacaoSaida(List<NewItemTransacaoSaidaDTO> listaNovosItens, TransacaoSaida transacaoSaida) {

        for (var novoItem : listaNovosItens) {
            var material = materialService.getEntityById(novoItem.idMaterial());

            criarNovoItem(novoItem, material, transacaoSaida);

        }

    }

    private boolean isMaterialPresent(long materialId, List<EditItemTransacaoSaidaDTO> listaAtualizadaDTO) {
        return listaAtualizadaDTO.stream()
                .anyMatch(dto -> dto.idMaterial() == materialId);
    }

    private ItemTransacaoSaida criarNovoItem(@Valid NewItemTransacaoSaidaDTO item, Material material, TransacaoSaida transacaoSaida) {
        ItemTransacaoSaida novoItem =  new ItemTransacaoSaida(
                null,
                material,
                transacaoSaida,
                item.undConsumo(),
                item.quantEntregue(),
                material.getValorUnt()
        );

        transacaoSaida.adicionarItem(novoItem);

        return novoItem;
    }
}
