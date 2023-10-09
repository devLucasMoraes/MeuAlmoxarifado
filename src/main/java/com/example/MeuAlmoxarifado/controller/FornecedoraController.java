package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.fornecedora.dto.request.EditFornecedoraDTO;
import com.example.MeuAlmoxarifado.controller.fornecedora.dto.request.NewFornecedoraDTO;
import com.example.MeuAlmoxarifado.controller.fornecedora.dto.response.ListFornecedoraDTO;
import com.example.MeuAlmoxarifado.controller.fornecedora.dto.response.ShowFornecedoraDTO;
import com.example.MeuAlmoxarifado.service.FornecedoraService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/fornecedoras")
public record FornecedoraController(FornecedoraService fornecedoraService) {

    @PostMapping("new")
    public ResponseEntity<ShowFornecedoraDTO> create(@RequestBody @Valid NewFornecedoraDTO newFornecedoraDTO) {
        var fornecedora = fornecedoraService.create(newFornecedoraDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fornecedora.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowFornecedoraDTO(fornecedora));
    }

    @GetMapping
    public ResponseEntity<List<ListFornecedoraDTO>> getAll() {
        var fornecedoras = fornecedoraService.findAll();
        var fornecedorasDTO = fornecedoras.stream().map(ListFornecedoraDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(fornecedorasDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowFornecedoraDTO> getById(@PathVariable Long id){
        var fornecedora = fornecedoraService.findById(id);
        return ResponseEntity.ok(new ShowFornecedoraDTO(fornecedora));
    }

    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowFornecedoraDTO> updateById(@PathVariable Long id, @RequestBody @Valid EditFornecedoraDTO editFornecedoraDTO) {
        var fornecedora = fornecedoraService.update(id, editFornecedoraDTO.toModel());
        return ResponseEntity.ok(new ShowFornecedoraDTO(fornecedora));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        fornecedoraService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
