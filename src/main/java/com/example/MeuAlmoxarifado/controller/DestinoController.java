package com.example.MeuAlmoxarifado.controller;

import com.example.MeuAlmoxarifado.controller.dto.destino.request.EditDestinoDTO;
import com.example.MeuAlmoxarifado.controller.dto.destino.request.NewDestinoDTO;
import com.example.MeuAlmoxarifado.controller.dto.destino.response.ListDestinoDTO;
import com.example.MeuAlmoxarifado.controller.dto.destino.response.ShowDestinoDTO;
import com.example.MeuAlmoxarifado.service.DestinoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("api/destinos")
public record DestinoController(DestinoService destinoService) {

    @PostMapping("new")
    public ResponseEntity<ShowDestinoDTO> create(@RequestBody @Valid NewDestinoDTO newDestinoDTO) {
        var destino = destinoService.create(newDestinoDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(destino.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowDestinoDTO(destino));
    }

    @GetMapping
    public ResponseEntity<List<ListDestinoDTO>> getAll() {
        var destinos = destinoService.findAll();
        var destinosDTO = destinos.stream().map(ListDestinoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(destinosDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowDestinoDTO> getById(@PathVariable Long id){
        var destino = destinoService.findById(id);
        return ResponseEntity.ok(new ShowDestinoDTO(destino));
    }


    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowDestinoDTO> updateById(@PathVariable Long id, @RequestBody @Valid EditDestinoDTO editDestinoDTO) {
        var destino = destinoService.update(id, editDestinoDTO.toModel());
        return ResponseEntity.ok(new ShowDestinoDTO(destino));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        destinoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
