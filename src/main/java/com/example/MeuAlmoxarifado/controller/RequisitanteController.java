package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.requisitante.dto.request.EditRequisitanteDTO;
import com.example.MeuAlmoxarifado.controller.requisitante.dto.request.NewRequisitanteDTO;
import com.example.MeuAlmoxarifado.controller.requisitante.dto.response.ListRequisitanteDTO;
import com.example.MeuAlmoxarifado.controller.requisitante.dto.response.ShowRequisitanteDTO;
import com.example.MeuAlmoxarifado.service.impl.RequisitanteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/requisitantes")
public class RequisitanteController {

    @Autowired
    private RequisitanteService requisitanteService;

    @PostMapping("new")
    public ResponseEntity<ShowRequisitanteDTO> create(@RequestBody @Valid NewRequisitanteDTO dados, UriComponentsBuilder componentsBuilder) {
        var dto = requisitanteService.create(dados);
        var uri = componentsBuilder.path("/requisitantes/show/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ListRequisitanteDTO>> getAll(Pageable pageable, @RequestParam(defaultValue = "") String nome) {
        var page = requisitanteService.getAll(pageable, nome);
        return ResponseEntity.ok(page);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowRequisitanteDTO> getById(@PathVariable Long id){
        var dto = requisitanteService.getById(id);
        return ResponseEntity.ok(dto);
    }


    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowRequisitanteDTO> updateById(@PathVariable Long id, @RequestBody @Valid EditRequisitanteDTO dados) {
        var dto = requisitanteService.updateById(id, dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        requisitanteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
