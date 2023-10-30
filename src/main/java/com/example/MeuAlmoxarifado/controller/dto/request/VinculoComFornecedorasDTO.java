package com.example.MeuAlmoxarifado.controller.dto.request;

import com.example.MeuAlmoxarifado.domain.model.Fornecedora;
import com.example.MeuAlmoxarifado.domain.model.VinculoMaterialComFornecedora;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record VinculoComFornecedorasDTO(
        Long idVinculo,
        @NotNull
        Long idFornecedora,
        @NotBlank
        String referenciaFornecedora,
        @NotBlank
        String descricaoFornecedora,
        @Valid
        List<ConversaoDeCompraDTO> conversoesDeCompra) {
    public VinculoMaterialComFornecedora toModel() {
        VinculoMaterialComFornecedora model = new VinculoMaterialComFornecedora();
        model.setId(this.idVinculo);
        model.setFornecedora(new Fornecedora(this.idFornecedora));
        model.setReferenciaFornecedora(this.referenciaFornecedora);
        model.setDescricaoFornecedora(this.descricaoFornecedora);
        model.setConversaoDeCompras(ofNullable(this.conversoesDeCompra)
                .orElse(emptyList())
                .stream().map(ConversaoDeCompraDTO::toModel).collect(toList()));
        return model;
    }
}
