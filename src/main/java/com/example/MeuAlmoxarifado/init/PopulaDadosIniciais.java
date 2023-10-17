package com.example.MeuAlmoxarifado.init;

import com.example.MeuAlmoxarifado.controller.dto.categoria.request.CategoriaDTO;
import com.example.MeuAlmoxarifado.controller.dto.fornecedora.request.FornecedoraDTO;
import com.example.MeuAlmoxarifado.controller.dto.material.request.MaterialDTO;
import com.example.MeuAlmoxarifado.controller.dto.transportadora.request.TransportadoraDTO;
import com.example.MeuAlmoxarifado.domain.model.Unidade;
import com.example.MeuAlmoxarifado.service.CategoriaService;
import com.example.MeuAlmoxarifado.service.FornecedoraService;
import com.example.MeuAlmoxarifado.service.MaterialService;
import com.example.MeuAlmoxarifado.service.TransportadoraService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;

@Component
public class PopulaDadosIniciais {

    private final CategoriaService categoriaService;

    private final MaterialService materialService;

    private final FornecedoraService fornecedoraService;

    private final TransportadoraService transportadoraService;

    public PopulaDadosIniciais(CategoriaService categoriaService, MaterialService materialService, FornecedoraService fornecedoraService, TransportadoraService transportadoraService) {
        this.categoriaService = categoriaService;
        this.materialService = materialService;
        this.fornecedoraService = fornecedoraService;
        this.transportadoraService = transportadoraService;
    }

    @PostConstruct
    public void populaDadosIniciais(){
        CategoriaDTO categoriaDTO = new CategoriaDTO(
                null,
                "ALCOOIS",
                Unidade.LITRO,
                new BigDecimal(150),
                new ArrayList<>()
        );

        MaterialDTO ipa7030 = new MaterialDTO(
                null,
                "IPA 70/30",
                true,
                null,
                1L,
                new ArrayList<>()
        );
        MaterialDTO ipa100 = new MaterialDTO(
                null,
                "IPA 100",
                true,
                null,
                1L,
                new ArrayList<>()
        );

        FornecedoraDTO fornecedoraDTO = new FornecedoraDTO(
                null,
                "68.958.487/0001-10",
                "CANOPUS IND E COM DE PRODUTOS",
                "CANOPUS QUIMICA",
                "11 4093-8300"
        );

        TransportadoraDTO transportadoraDTO = new TransportadoraDTO(
                null,
                "35.653.525.0001-00",
                "ADRIANA DIMITROV TRANSPORTES - ME",
                "SORA TRANSPORTES",
                "11 4063-8826"
        );

        fornecedoraService.create(fornecedoraDTO.toModel());
        transportadoraService.create(transportadoraDTO.toModel());
        categoriaService.create(categoriaDTO.toModel());
        materialService.create(ipa7030.toModel());
        materialService.create(ipa100.toModel());
    }
}
