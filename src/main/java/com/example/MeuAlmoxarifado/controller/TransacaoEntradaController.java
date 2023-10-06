package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.compra.dto.request.EditCompraDTO;
import com.example.MeuAlmoxarifado.controller.compra.dto.request.NewCompraDTO;
import com.example.MeuAlmoxarifado.controller.compra.dto.response.ListCompraDTO;
import com.example.MeuAlmoxarifado.controller.compra.dto.response.ShowCompraDTO;
import com.example.MeuAlmoxarifado.service.impl.CompraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/transacoes_entrada")
public class TransacaoEntradaController {

    @Autowired
    private CompraService compraService;

    @PostMapping("new")
    public ResponseEntity<ShowCompraDTO> create(@RequestBody @Valid NewCompraDTO dados, UriComponentsBuilder componentsBuilder) {
        var dto = compraService.create(dados);
        var uri = componentsBuilder.path("/transacoes_entrada/show/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ListCompraDTO>> getAll(Pageable pageable) {
        var page = compraService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowCompraDTO> getById(@PathVariable Long id) {
        var dto = compraService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowCompraDTO> updateById(@PathVariable Long id, @RequestBody @Valid EditCompraDTO dados) {
        var dto = compraService.updateById(id,dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        compraService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
