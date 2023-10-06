package com.example.MeuAlmoxarifado.controller.vinculoMaterialComFornecedora.dto.response;


import com.example.MeuAlmoxarifado.controller.conversaoDeCompra.dto.response.ShowConversaoDeCompraDTO;

import java.util.List;

public record ShowVinculoComFornecedorasDTO(

        Long id,

        Long idFornecedora,

        Long idMaterial,

        String codProd,

        List<ShowConversaoDeCompraDTO> conversoesDeCompra
) {
}
