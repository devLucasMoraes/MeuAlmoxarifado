package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.dto.request.RequisitanteDTO;
import com.example.MeuAlmoxarifado.controller.dto.response.ShowRequisitanteDTO;
import com.example.MeuAlmoxarifado.service.RequisitanteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/requisitantes")
@CrossOrigin
public record RequisitanteController(RequisitanteService requisitanteService) {

    @PostMapping("new")
    public ResponseEntity<ShowRequisitanteDTO> create(@RequestBody @Valid RequisitanteDTO requisitanteDTO) {
        var requisitante = requisitanteService.create(requisitanteDTO.toNewModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(requisitante.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowRequisitanteDTO(requisitante));
    }

    @GetMapping
    public ResponseEntity<Page<ShowRequisitanteDTO>> getAll(Pageable pageable) {
        var requisitantes = requisitanteService.findAll(pageable);
        var requisitantesDTO = requisitantes.map(ShowRequisitanteDTO::new);
        return ResponseEntity.ok(requisitantesDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowRequisitanteDTO> getById(@PathVariable Long id){
        var requisitante = requisitanteService.findById(id);
        return ResponseEntity.ok(new ShowRequisitanteDTO(requisitante));
    }


    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowRequisitanteDTO> updateById(@PathVariable Long id, @RequestBody @Valid RequisitanteDTO requisitanteDTO) {
        var requisitante = requisitanteService.update(id, requisitanteDTO.toModel());
        return ResponseEntity.ok(new ShowRequisitanteDTO(requisitante));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        requisitanteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
