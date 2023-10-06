package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.material.dto.request.EditMaterialDTO;
import com.example.MeuAlmoxarifado.controller.material.dto.request.NewMaterialDTO;
import com.example.MeuAlmoxarifado.controller.material.dto.response.ListMateriaisDTO;
import com.example.MeuAlmoxarifado.controller.material.dto.response.ShowMaterialDTO;
import com.example.MeuAlmoxarifado.service.impl.MaterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/materiais")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @PostMapping("new")
    public ResponseEntity<ShowMaterialDTO> create(@RequestBody @Valid NewMaterialDTO dados, UriComponentsBuilder componentsBuilder) {
        var dto = materialService.create(dados);
        var uri = componentsBuilder.path("/materiais/show/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ListMateriaisDTO>> getAll(Pageable pageable, @RequestParam(defaultValue = "") String descricao) {
        var page = materialService.getAll(pageable, descricao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowMaterialDTO> getById(@PathVariable Long id) {
        var dto = materialService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("show/cod_prod/{codProd}")
    public ResponseEntity<ShowMaterialDTO> getByCodProd(@PathVariable String codProd) {
        var dto = materialService.getByCodProd(codProd);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowMaterialDTO> updateById(@PathVariable Long id, @RequestBody @Valid EditMaterialDTO dados) {
        var dto = materialService.updateById(id, dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        materialService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
