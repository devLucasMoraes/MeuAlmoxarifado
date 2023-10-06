package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.controller.conversaoDeCompra.dto.request.EditConversaoDeCompraDTO;
import com.example.MeuAlmoxarifado.controller.conversaoDeCompra.dto.request.NewConversaoDeCompraDTO;
import com.example.MeuAlmoxarifado.domain.model.ConversaoDeCompra;
import com.example.MeuAlmoxarifado.domain.model.Unidade;
import com.example.MeuAlmoxarifado.domain.model.VinculoMaterialComFornecedora;
import com.example.MeuAlmoxarifado.domain.repository.ConversaoDeCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversaoDeCompraService {

    @Autowired
    private ConversaoDeCompraRepository conversaoDeCompraRepository;

    @Transactional
    public void atualizarConversoesDeCompra(List<EditConversaoDeCompraDTO> listaAtualizadaDTO, VinculoMaterialComFornecedora vinculo) {

        List<ConversaoDeCompra> itensParaRemover = new ArrayList<>();

        for (var item : vinculo.getConversaoDeCompras()) {
            var undCompra = item.getUndCompra();

            if (!isUndCompraPresent(undCompra, listaAtualizadaDTO)) {
                System.out.println(undCompra);
                itensParaRemover.add(item);
            }
        }

        vinculo.getConversaoDeCompras().removeAll(itensParaRemover);
        conversaoDeCompraRepository.deleteAll(itensParaRemover);

        for (var conversaoAtualizada : listaAtualizadaDTO) {

            ConversaoDeCompra conversaoDeCompra = vinculo.getConversaoDeCompras().stream()
                    .filter(item -> item.getUndCompra().equals(conversaoAtualizada.undCompra()))
                    .findFirst()
                    .orElseGet(() -> criarNovaConversao(new NewConversaoDeCompraDTO(conversaoAtualizada), vinculo));

            conversaoDeCompra.update(conversaoAtualizada, vinculo);

        }

    }

    @Transactional
    public void adicionarConversoesDeCompra(ArrayList<NewConversaoDeCompraDTO> ListaNovasConversaoesDeCompra, VinculoMaterialComFornecedora vinculo) {

        for (var novaConversao : ListaNovasConversaoesDeCompra) {

            criarNovaConversao(novaConversao, vinculo);

        }
    }

    private ConversaoDeCompra criarNovaConversao(NewConversaoDeCompraDTO novaConversao, VinculoMaterialComFornecedora vinculo) {
        var conversao = new ConversaoDeCompra(
                null,
                novaConversao.undCompra(),
                novaConversao.undEstoque(),
                novaConversao.fatorDeConversao(),
                vinculo
        );

        vinculo.adicionarConversao(conversao);

        return conversao;
    }

    private boolean isUndCompraPresent(Unidade undCompra, List<EditConversaoDeCompraDTO> listaAtualizadaDTO) {

        return listaAtualizadaDTO.stream()
                .anyMatch(dto -> dto.undCompra() == undCompra);
    }

}
