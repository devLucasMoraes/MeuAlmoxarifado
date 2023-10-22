package com.example.MeuAlmoxarifado.init;

import com.example.MeuAlmoxarifado.controller.dto.request.CategoriaDTO;
import com.example.MeuAlmoxarifado.controller.dto.request.FornecedoraDTO;
import com.example.MeuAlmoxarifado.controller.dto.request.MaterialDTO;
import com.example.MeuAlmoxarifado.controller.dto.request.TransportadoraDTO;
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
        categoriaService.create(alcoois.toModel());
        categoriaService.create(papelaoParana.toModel());
        materialService.create(ipa7030.toModel());
        materialService.create(ipa100.toModel());
        materialService.create(papelao08100015.toModel());
    }
}
