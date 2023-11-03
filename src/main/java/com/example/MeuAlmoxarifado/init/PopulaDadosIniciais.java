package com.example.MeuAlmoxarifado.init;

import com.example.MeuAlmoxarifado.controller.dto.request.*;
import com.example.MeuAlmoxarifado.domain.model.Unidade;
import com.example.MeuAlmoxarifado.service.*;
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

    private final RequisitanteService requisitanteService;

    private final LocalDeAplicacaoService localDeAplicacaoService;

    public PopulaDadosIniciais(CategoriaService categoriaService, MaterialService materialService, FornecedoraService fornecedoraService, TransportadoraService transportadoraService, RequisitanteService requisitanteService, LocalDeAplicacaoService localDeAplicacaoService) {
        this.categoriaService = categoriaService;
        this.materialService = materialService;
        this.fornecedoraService = fornecedoraService;
        this.transportadoraService = transportadoraService;
        this.requisitanteService = requisitanteService;
        this.localDeAplicacaoService = localDeAplicacaoService;
    }

    @PostConstruct
    public void populaDadosIniciais(){
        CategoriaDTO alcoois = new CategoriaDTO(
                null,
                "ALCOOIS",
                Unidade.LITRO,
                new BigDecimal(150),
                new ArrayList<>()
        );
        CategoriaDTO papelaoParana = new CategoriaDTO(
                null,
                "PAPELAO PARANA",
                Unidade.UNID,
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
        MaterialDTO papelao08100015 = new MaterialDTO(
                null,
                "PAPELAO 800 x 1000mm 1.5mm",
                true,
                null,
                2L,
                new ArrayList<>()
        );

        FornecedoraDTO canopusQuimica = new FornecedoraDTO(
                null,
                "68958487000153",
                "CANOPUS IND E COM DE PRODUTOS",
                "CANOPUS QUIMICA",
                "11 4093-8300"
        );

        FornecedoraDTO eurustar = new FornecedoraDTO(
                null,
                "28859855000191",
                "EUROSTAR LTDA",
                "EUROSTAR",
                "11 4093-8300"
        );

        TransportadoraDTO transportadoraDTO = new TransportadoraDTO(
                null,
                "35.653.525.0001-00",
                "ADRIANA DIMITROV TRANSPORTES - ME",
                "SORA TRANSPORTES",
                "11 4063-8826"
        );

        RequisitanteDTO requisitanteDTO = new RequisitanteDTO(
                null,
                "ROGERIO",
                "(xx)x xxxx-xxxx"
        );

        LocalDeAplicacaoDTO localDeAplicacaoDTO = new LocalDeAplicacaoDTO(
                null,
                "SM102"
        );

        fornecedoraService.create(eurustar.toModel());
        fornecedoraService.create(canopusQuimica.toModel());
        transportadoraService.create(transportadoraDTO.toModel());
        categoriaService.create(alcoois.toModel());
        categoriaService.create(papelaoParana.toModel());
        materialService.create(ipa7030.toModel());
        materialService.create(ipa100.toModel());
        materialService.create(papelao08100015.toModel());
        requisitanteService.create(requisitanteDTO.toNewModel());
        localDeAplicacaoService.create(localDeAplicacaoDTO.toNewModel());
    }
}
