package com.example.MeuAlmoxarifado.controller;

import com.example.MeuAlmoxarifado.controller.dto.request.LocalDTO;
import com.example.MeuAlmoxarifado.controller.dto.response.ShowLocalDTO;
import com.example.MeuAlmoxarifado.service.LocalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("api/locais")
public record LocalController(LocalService localService) {

    @PostMapping("new")
    public ResponseEntity<ShowLocalDTO> create(@RequestBody @Valid LocalDTO localDTO) {
        var destino = localService.create(localDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(destino.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowLocalDTO(destino));
    }

    @GetMapping
    public ResponseEntity<List<ShowLocalDTO>> getAll() {
        var locais = localService.findAll();
        var locaisDTO = locais.stream().map(ShowLocalDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(locaisDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowLocalDTO> getById(@PathVariable Long id){
        var local = localService.findById(id);
        return ResponseEntity.ok(new ShowLocalDTO(local));
    }


    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowLocalDTO> updateById(@PathVariable Long id, @RequestBody @Valid LocalDTO localDTO) {
        var local = localService.update(id, localDTO.toModel());
        return ResponseEntity.ok(new ShowLocalDTO(local));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        localService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
