package com.example.MeuAlmoxarifado.controller.dto.vinculoMaterialComFornecedora.request;

import com.example.MeuAlmoxarifado.controller.dto.conversaoDeCompra.request.ConversaoDeCompraDTO;
import com.example.MeuAlmoxarifado.domain.model.Fornecedora;
import com.example.MeuAlmoxarifado.domain.model.VinculoMaterialComFornecedora;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record VinculoComFornecedorasDTO(
        Long id,
        @NotNull
        Long idFornecedora,
        @NotBlank
        String codProd,
        List<ConversaoDeCompraDTO> conversoesDeCompra) {
    public VinculoMaterialComFornecedora toModel() {
        VinculoMaterialComFornecedora model = new VinculoMaterialComFornecedora();
        model.setId(this.id);
        model.setFornecedora(new Fornecedora(this.idFornecedora));
        model.setCodProd(this.codProd);
        model.setConversaoDeCompras(ofNullable(this.conversoesDeCompra)
                .orElse(emptyList())
                .stream().map(ConversaoDeCompraDTO::toModel).collect(toList()));
        return model;
    }
}
