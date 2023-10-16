package com.example.MeuAlmoxarifado.init;

import com.example.MeuAlmoxarifado.controller.dto.categoria.request.CategoriaDTO;
import com.example.MeuAlmoxarifado.controller.dto.material.request.MaterialDTO;
import com.example.MeuAlmoxarifado.domain.model.Unidade;
import com.example.MeuAlmoxarifado.service.CategoriaService;
import com.example.MeuAlmoxarifado.service.MaterialService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;

@Component
public class PopulaDadosIniciais {

    private final CategoriaService categoriaService;

    private final MaterialService materialService;

    public PopulaDadosIniciais(CategoriaService categoriaService, MaterialService materialService) {
        this.categoriaService = categoriaService;
        this.materialService = materialService;
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
        categoriaService.create(categoriaDTO.toModel());

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
        materialService.create(ipa7030.toModel());
        materialService.create(ipa100.toModel());
    }
}
