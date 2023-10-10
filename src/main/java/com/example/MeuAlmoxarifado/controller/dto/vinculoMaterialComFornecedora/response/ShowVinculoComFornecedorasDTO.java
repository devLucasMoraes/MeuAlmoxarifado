package com.example.MeuAlmoxarifado.controller.dto.vinculoMaterialComFornecedora.response;


import com.example.MeuAlmoxarifado.controller.dto.conversaoDeCompra.response.ShowConversaoDeCompraDTO;

import java.util.List;

public record ShowVinculoComFornecedorasDTO(

        Long id,

        Long idFornecedora,

        Long idMaterial,

        String codProd,

        List<ShowConversaoDeCompraDTO> conversoesDeCompra
) {
}
