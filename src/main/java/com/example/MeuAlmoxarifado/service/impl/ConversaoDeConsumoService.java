package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.controller.conversaoDeConsumo.dto.request.EditConversaoDeConsumoDTO;
import com.example.MeuAlmoxarifado.controller.conversaoDeConsumo.dto.request.NewConversaoDeConsumoDTO;
import com.example.MeuAlmoxarifado.domain.model.Categoria;
import com.example.MeuAlmoxarifado.domain.model.ConversaoDeConsumo;
import com.example.MeuAlmoxarifado.domain.repository.ConversaoDeConsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversaoDeConsumoService {

    @Autowired
    private ConversaoDeConsumoRepository conversaoDeConsumoRepository;


    @Transactional
    public void atualizarConversoesDeConsumo(List<EditConversaoDeConsumoDTO> listaAtualizadaDTO, Categoria categoria) {

        List<ConversaoDeConsumo> itensParaRemover = new ArrayList<>();

        for (var oldConversao : categoria.getConversoesDeConsumo()) {

            if (!isConversaoPresent(oldConversao, listaAtualizadaDTO)) {

                itensParaRemover.add(oldConversao);

            }
        }

        categoria.getConversoesDeConsumo().removeAll(itensParaRemover);
        conversaoDeConsumoRepository.deleteAll(itensParaRemover);

        for (var conversaoAtualizada : listaAtualizadaDTO) {
            if(categoria.getUndEstoque() == conversaoAtualizada.undEstoque()) {
                ConversaoDeConsumo conversaoDeConsumo = categoria.getConversoesDeConsumo().stream()
                        .filter(item -> item.getUndConsumo().equals(conversaoAtualizada.undConsumo()))
                        .filter(item -> item.getUndEstoque().equals(conversaoAtualizada.undEstoque()))
                        .findFirst()
                        .orElseGet(() -> criarNovaConversao(new NewConversaoDeConsumoDTO(conversaoAtualizada), categoria));

                conversaoDeConsumo.update(conversaoAtualizada, categoria);

            }

        }

    }

    @Transactional
    public void adicionarConversoesDeConsumo(ArrayList<NewConversaoDeConsumoDTO> ListaNovasConversaoesDeConsumo, Categoria categoria) {

        for (var novaConversao : ListaNovasConversaoesDeConsumo) {

            criarNovaConversao(novaConversao, categoria);

        }
    }

    private ConversaoDeConsumo criarNovaConversao(NewConversaoDeConsumoDTO novaConversao, Categoria categoria) {
        var conversao = new ConversaoDeConsumo(
                null,
                novaConversao.undConsumo(),
                categoria.getUndEstoque(),
                novaConversao.fatorDeConversao(),
                categoria
        );

        categoria.adicionarConversao(conversao);

        return conversao;
    }

    private boolean isConversaoPresent(ConversaoDeConsumo conversaoDeConsumo, List<EditConversaoDeConsumoDTO> listaAtualizadaDTO) {

        return listaAtualizadaDTO.stream()
                .anyMatch(dto -> dto.undConsumo() == conversaoDeConsumo.getUndConsumo() &&
                        dto.undEstoque() == conversaoDeConsumo.getUndEstoque());
    }


}
