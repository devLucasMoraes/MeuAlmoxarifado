package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.fornecedora.dto.request.EditFornecedoraDTO;
import com.example.MeuAlmoxarifado.controller.fornecedora.dto.request.NewFornecedoraDTO;
import com.example.MeuAlmoxarifado.controller.fornecedora.dto.response.ListFornecedoraDTO;
import com.example.MeuAlmoxarifado.controller.fornecedora.dto.response.ShowFornecedoraDTO;
import com.example.MeuAlmoxarifado.service.impl.FornecedoraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/fornecedoras")
public class FornecedoraController {

    @Autowired
    private FornecedoraService fornecedoraService;

    @PostMapping("new")
    public ResponseEntity<ShowFornecedoraDTO> create(@RequestBody @Valid NewFornecedoraDTO dados, UriComponentsBuilder componentsBuilder) {
        var dto = fornecedoraService.create(dados);
        var uri = componentsBuilder.path("/fornecedoras/show/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ListFornecedoraDTO>> getAll(Pageable pageable, @RequestParam(defaultValue = "") String nomeFantasia) {
        var page = fornecedoraService.getAll(pageable, nomeFantasia);
        return ResponseEntity.ok(page);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowFornecedoraDTO> getById(@PathVariable Long id){
        var dto = fornecedoraService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("show/cnpj/{cnpj}")
    public ResponseEntity<ShowFornecedoraDTO> getByCnpj(@PathVariable String cnpj){
        var dto = fornecedoraService.getByCnpj(cnpj);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowFornecedoraDTO> updateById(@PathVariable Long id, @RequestBody @Valid EditFornecedoraDTO dados) {
        var dto = fornecedoraService.updateById(id, dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        fornecedoraService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
