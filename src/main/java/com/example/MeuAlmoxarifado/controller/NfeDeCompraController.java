package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.dto.request.NfeDeCompraDTO;
import com.example.MeuAlmoxarifado.controller.dto.response.ShowNfeDeCompraDTO;
import com.example.MeuAlmoxarifado.service.NfeDeCompraService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/nfe_de_compra")
public record NfeDeCompraController(NfeDeCompraService nfeDeCompraService) {


    @PostMapping("/new")
    public ResponseEntity<ShowNfeDeCompraDTO> create(@RequestBody @Valid NfeDeCompraDTO nfeDeCompraDTO) {
        var nfeDeCompra = nfeDeCompraService.create(nfeDeCompraDTO.toNewModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nfeDeCompra.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowNfeDeCompraDTO(nfeDeCompra));
    }

    @GetMapping
    public ResponseEntity<List<ShowNfeDeCompraDTO>> getAll() {
        var nfesDeCompra = nfeDeCompraService.findAll();
        var nfesDeCompraDTO = nfesDeCompra.stream().map(ShowNfeDeCompraDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(nfesDeCompraDTO);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<ShowNfeDeCompraDTO> getById(@PathVariable Long id) {
        var nfeDeCompra = nfeDeCompraService.findById(id);
        return ResponseEntity.ok(new ShowNfeDeCompraDTO(nfeDeCompra));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ShowNfeDeCompraDTO> updateById(@PathVariable Long id, @RequestBody @Valid NfeDeCompraDTO nfeDeCompraDTO) {
        var nfeDeCompra = nfeDeCompraService.update(id, nfeDeCompraDTO.toModel());
        return ResponseEntity.ok(new ShowNfeDeCompraDTO(nfeDeCompra));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        nfeDeCompraService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
