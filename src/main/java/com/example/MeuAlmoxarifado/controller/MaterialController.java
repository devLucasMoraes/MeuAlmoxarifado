package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.material.dto.request.EditMaterialDTO;
import com.example.MeuAlmoxarifado.controller.material.dto.request.NewMaterialDTO;
import com.example.MeuAlmoxarifado.controller.material.dto.response.ListMateriaisDTO;
import com.example.MeuAlmoxarifado.controller.material.dto.response.ShowMaterialDTO;
import com.example.MeuAlmoxarifado.service.MaterialService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/materiais")
public record MaterialController(MaterialService materialService) {

    @PostMapping("new")
    public ResponseEntity<ShowMaterialDTO> create(@RequestBody @Valid NewMaterialDTO newMaterialDTO) {
        var material = materialService.create(newMaterialDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(material.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowMaterialDTO(material));
    }

    @GetMapping
    public ResponseEntity<List<ListMateriaisDTO>> getAll() {
        var materiais = materialService.findAll();
        var materiaisDTO = materiais.stream().map(ListMateriaisDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(materiaisDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowMaterialDTO> getById(@PathVariable Long id) {
        var material = materialService.findById(id);
        return ResponseEntity.ok(new ShowMaterialDTO(material));
    }


    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowMaterialDTO> updateById(@PathVariable Long id, @RequestBody @Valid EditMaterialDTO editMaterialDTO) {
        var material = materialService.update(id, editMaterialDTO.toModel());
        return ResponseEntity.ok(new ShowMaterialDTO(material));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        materialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
