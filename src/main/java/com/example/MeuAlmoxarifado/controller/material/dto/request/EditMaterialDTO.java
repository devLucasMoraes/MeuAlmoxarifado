package com.example.MeuAlmoxarifado.controller.material.dto.request;


import com.example.MeuAlmoxarifado.controller.vinculoMaterialComFornecedora.dto.request.EditVinculoComFornecedorasDTO;

import java.math.BigDecimal;
import java.util.ArrayList;

public record EditMaterialDTO(


        Long id,

        String descricao,

        BigDecimal valorUnt,

        Long idCategoria,

        ArrayList<EditVinculoComFornecedorasDTO> fornecedorasVinculadas
) {
}
