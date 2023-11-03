package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.dto.request.FornecedoraDTO;
import com.example.MeuAlmoxarifado.controller.dto.response.ShowFornecedoraDTO;
import com.example.MeuAlmoxarifado.controller.filter.FornecedoraSearchFilter;
import com.example.MeuAlmoxarifado.service.FornecedoraService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/fornecedoras")
@CrossOrigin
public record FornecedoraController(FornecedoraService fornecedoraService) {

    @PostMapping("new")
    public ResponseEntity<ShowFornecedoraDTO> create(@RequestBody @Valid FornecedoraDTO fornecedoraDTO) {
        var fornecedora = fornecedoraService.create(fornecedoraDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fornecedora.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowFornecedoraDTO(fornecedora));
    }

    @GetMapping
    public ResponseEntity<Page<ShowFornecedoraDTO>> getAll(Pageable pageable) {
        var fornecedoras = fornecedoraService.findAll(pageable);
        var fornecedorasDTO = fornecedoras.map(ShowFornecedoraDTO::new);
        return ResponseEntity.ok(fornecedorasDTO);
    }

    @GetMapping("/query")
    public ResponseEntity<Page<ShowFornecedoraDTO>> dynamicGetAll(FornecedoraSearchFilter filter, Pageable pageable) {
        var fornecedoras = fornecedoraService.dynamicFindAll(filter.toSpec(),pageable);
        var fornecedorasDTO = fornecedoras.map(ShowFornecedoraDTO::new);
        return ResponseEntity.ok(fornecedorasDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowFornecedoraDTO> getById(@PathVariable Long id){
        var fornecedora = fornecedoraService.findById(id);
        return ResponseEntity.ok(new ShowFornecedoraDTO(fornecedora));
    }

    @GetMapping("show/cnpj/{cnpj}")
    public ResponseEntity<ShowFornecedoraDTO> getByCnpj(@PathVariable String cnpj){
        var fornecedora = fornecedoraService.getByCnpj(cnpj);
        return ResponseEntity.ok(new ShowFornecedoraDTO(fornecedora));
    }

    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowFornecedoraDTO> updateById(@PathVariable Long id, @RequestBody @Valid FornecedoraDTO fornecedoraDTO) {
        var fornecedora = fornecedoraService.update(id, fornecedoraDTO.toModel());
        return ResponseEntity.ok(new ShowFornecedoraDTO(fornecedora));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        fornecedoraService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
