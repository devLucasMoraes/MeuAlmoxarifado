package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.dto.compra.request.CompraDTO;
import com.example.MeuAlmoxarifado.controller.dto.compra.response.ListCompraDTO;
import com.example.MeuAlmoxarifado.controller.dto.compra.response.ShowCompraDTO;
import com.example.MeuAlmoxarifado.service.CompraService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/transacoes_entrada")
public record CompraController(CompraService compraService) {


    @PostMapping("new")
    public ResponseEntity<ShowCompraDTO> create(@RequestBody @Valid CompraDTO compraDTO) {
        var nfeDeCompra = compraService.create(compraDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nfeDeCompra.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowCompraDTO(nfeDeCompra));
    }

    @GetMapping
    public ResponseEntity<List<ListCompraDTO>> getAll() {
        var nfesDeCompra = compraService.findAll();
        var nfesDeCompraDTO = nfesDeCompra.stream().map(ListCompraDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(nfesDeCompraDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowCompraDTO> getById(@PathVariable Long id) {
        var nfeDeCompra = compraService.findById(id);
        return ResponseEntity.ok(new ShowCompraDTO(nfeDeCompra));
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<ShowCompraDTO> updateById(@PathVariable Long id, @RequestBody @Valid CompraDTO compraDTO) {
        var nfeDeCompra = compraService.update(id, compraDTO.toModel());
        return ResponseEntity.ok(new ShowCompraDTO(nfeDeCompra));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        compraService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
