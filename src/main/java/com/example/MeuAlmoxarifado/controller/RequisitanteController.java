package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.dto.requisitante.request.EditRequisitanteDTO;
import com.example.MeuAlmoxarifado.controller.dto.requisitante.request.NewRequisitanteDTO;
import com.example.MeuAlmoxarifado.controller.dto.requisitante.response.ListRequisitanteDTO;
import com.example.MeuAlmoxarifado.controller.dto.requisitante.response.ShowRequisitanteDTO;
import com.example.MeuAlmoxarifado.service.RequisitanteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/requisitantes")
public record RequisitanteController(RequisitanteService requisitanteService) {

    @PostMapping("new")
    public ResponseEntity<ShowRequisitanteDTO> create(@RequestBody @Valid NewRequisitanteDTO newRequisitanteDTO) {
        var requisitante = requisitanteService.create(newRequisitanteDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(requisitante.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowRequisitanteDTO(requisitante));
    }

    @GetMapping
    public ResponseEntity<List<ListRequisitanteDTO>> getAll() {
        var requisitantes = requisitanteService.findAll();
        var requisitantesDTO = requisitantes.stream().map(ListRequisitanteDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(requisitantesDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowRequisitanteDTO> getById(@PathVariable Long id){
        var requisitante = requisitanteService.findById(id);
        return ResponseEntity.ok(new ShowRequisitanteDTO(requisitante));
    }


    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowRequisitanteDTO> updateById(@PathVariable Long id, @RequestBody @Valid EditRequisitanteDTO editRequisitanteDTO) {
        var requisitante = requisitanteService.update(id, editRequisitanteDTO.toModel());
        return ResponseEntity.ok(new ShowRequisitanteDTO(requisitante));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        requisitanteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
