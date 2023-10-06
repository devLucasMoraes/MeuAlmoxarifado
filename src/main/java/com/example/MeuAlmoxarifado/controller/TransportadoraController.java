package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.transportadora.dto.request.EditTransportadoraDTO;
import com.example.MeuAlmoxarifado.controller.transportadora.dto.request.NewTransportadoraDTO;
import com.example.MeuAlmoxarifado.controller.transportadora.dto.response.ListTransportadoraDTO;
import com.example.MeuAlmoxarifado.controller.transportadora.dto.response.ShowTransportadoraDTO;
import com.example.MeuAlmoxarifado.service.impl.TransportadoraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/transportadoras")
public class TransportadoraController {

    @Autowired
    private TransportadoraService transportadoraService;

    @PostMapping("new")
    public ResponseEntity<ShowTransportadoraDTO> create(@RequestBody @Valid NewTransportadoraDTO dados, UriComponentsBuilder componentsBuilder) {
        var dto = transportadoraService.create(dados);
        var uri = componentsBuilder.path("/transportadoras/show/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ListTransportadoraDTO>> getAll(Pageable pageable, @RequestParam(defaultValue = "") String nomeFantasia) {
        var page = transportadoraService.getAll(pageable, nomeFantasia);
        return ResponseEntity.ok(page);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowTransportadoraDTO> getById(@PathVariable Long id){
        var dto = transportadoraService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("show/cnpj/{cnpj}")
    public ResponseEntity<ShowTransportadoraDTO> getByCnpj(@PathVariable String cnpj){
        var dto = transportadoraService.getByCnpj(cnpj);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowTransportadoraDTO> updateById(@PathVariable Long id, @RequestBody @Valid EditTransportadoraDTO dados) {
        var dto = transportadoraService.updateById(id, dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        transportadoraService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
