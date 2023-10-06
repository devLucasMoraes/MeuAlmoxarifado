package com.example.MeuAlmoxarifado.controller.vinculoMaterialComFornecedora.dto.request;


import com.example.MeuAlmoxarifado.controller.conversaoDeCompra.dto.request.EditConversaoDeCompraDTO;

import java.util.ArrayList;

public record EditVinculoComFornecedorasDTO(


        Long id,

        Long idFornecedora,

        String codProd,

        ArrayList<EditConversaoDeCompraDTO> conversoesDeCompra
) {
}
