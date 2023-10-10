package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.dto.transportadora.request.EditTransportadoraDTO;
import com.example.MeuAlmoxarifado.controller.dto.transportadora.request.NewTransportadoraDTO;
import com.example.MeuAlmoxarifado.controller.dto.transportadora.response.ListTransportadoraDTO;
import com.example.MeuAlmoxarifado.controller.dto.transportadora.response.ShowTransportadoraDTO;
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
public record TransportadoraController(TransportadoraService transportadoraService) {

    @PostMapping("new")
    public ResponseEntity<ShowTransportadoraDTO> create(@RequestBody @Valid NewTransportadoraDTO newTransportadoraDTO) {
        var transportadora = transportadoraService.create(newTransportadoraDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(transportadora.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowTransportadoraDTO(transportadora));
    }

    @GetMapping
    public ResponseEntity<List<ListTransportadoraDTO>> getAll() {
        var transportadoras = transportadoraService.findAll();
        var transportadorasDTO = transportadoras.stream().map(ListTransportadoraDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(transportadorasDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowTransportadoraDTO> getById(@PathVariable Long id){
        var transportadora = transportadoraService.findById(id);
        return ResponseEntity.ok(new ShowTransportadoraDTO(transportadora));
    }


    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowTransportadoraDTO> updateById(@PathVariable Long id, @RequestBody @Valid EditTransportadoraDTO editTransportadoraDTO) {
        var transportadora = transportadoraService.update(id, editTransportadoraDTO.toModel());
        return ResponseEntity.ok(new ShowTransportadoraDTO(transportadora));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        transportadoraService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
