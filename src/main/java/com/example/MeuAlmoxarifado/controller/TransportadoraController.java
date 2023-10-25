package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.dto.request.TransportadoraDTO;
import com.example.MeuAlmoxarifado.controller.dto.response.ShowTransportadoraDTO;
import com.example.MeuAlmoxarifado.service.TransportadoraService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/transportadoras")
@CrossOrigin
public record TransportadoraController(TransportadoraService transportadoraService) {

    @PostMapping("new")
    public ResponseEntity<ShowTransportadoraDTO> create(@RequestBody @Valid TransportadoraDTO transportadoraDTO) {
        var transportadora = transportadoraService.create(transportadoraDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(transportadora.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowTransportadoraDTO(transportadora));
    }

    @GetMapping
    public ResponseEntity<List<ShowTransportadoraDTO>> getAll() {
        var transportadoras = transportadoraService.findAll();
        var transportadorasDTO = transportadoras.stream().map(ShowTransportadoraDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(transportadorasDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowTransportadoraDTO> getById(@PathVariable Long id){
        var transportadora = transportadoraService.findById(id);
        return ResponseEntity.ok(new ShowTransportadoraDTO(transportadora));
    }


    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowTransportadoraDTO> updateById(@PathVariable Long id, @RequestBody @Valid TransportadoraDTO transportadoraDTO) {
        var transportadora = transportadoraService.update(id, transportadoraDTO.toModel());
        return ResponseEntity.ok(new ShowTransportadoraDTO(transportadora));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        transportadoraService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
