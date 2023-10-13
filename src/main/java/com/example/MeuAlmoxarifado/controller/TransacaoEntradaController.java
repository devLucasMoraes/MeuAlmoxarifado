package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.dto.compra.request.NfeDeCompraDTO;
import com.example.MeuAlmoxarifado.controller.dto.compra.response.ListCompraDTO;
import com.example.MeuAlmoxarifado.controller.dto.compra.response.ShowCompraDTO;
import com.example.MeuAlmoxarifado.controller.dto.emprestimo.EmprestimoDTO;
import com.example.MeuAlmoxarifado.controller.dto.emprestimo.ShowEmprestimoDTO;
import com.example.MeuAlmoxarifado.service.EmprestimoService;
import com.example.MeuAlmoxarifado.service.NfeDeCompraService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/transacoes_entrada")
public record TransacaoEntradaController(NfeDeCompraService nfeDeCompraService, EmprestimoService emprestimoService) {


    @PostMapping("/nfe_de_compra/new")
    public ResponseEntity<ShowCompraDTO> create(@RequestBody @Valid NfeDeCompraDTO nfeDeCompraDTO) {
        var nfeDeCompra = nfeDeCompraService.create(nfeDeCompraDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nfeDeCompra.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowCompraDTO(nfeDeCompra));
    }

    @GetMapping("/nfe_de_compra")
    public ResponseEntity<List<ListCompraDTO>> getAll() {
        var nfesDeCompra = nfeDeCompraService.findAll();
        var nfesDeCompraDTO = nfesDeCompra.stream().map(ListCompraDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(nfesDeCompraDTO);
    }

    @GetMapping("/nfe_de_compra/show/{id}")
    public ResponseEntity<ShowCompraDTO> getById(@PathVariable Long id) {
        var nfeDeCompra = nfeDeCompraService.findById(id);
        return ResponseEntity.ok(new ShowCompraDTO(nfeDeCompra));
    }

    @PutMapping("/nfe_de_compra/edit/{id}")
    public ResponseEntity<ShowCompraDTO> updateById(@PathVariable Long id, @RequestBody @Valid NfeDeCompraDTO nfeDeCompraDTO) {
        var nfeDeCompra = nfeDeCompraService.update(id, nfeDeCompraDTO.toModel());
        return ResponseEntity.ok(new ShowCompraDTO(nfeDeCompra));
    }

    @DeleteMapping("/nfe_de_compra/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        nfeDeCompraService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/emprestimo/new")
    public ResponseEntity<ShowEmprestimoDTO> create(@RequestBody @Valid EmprestimoDTO emprestimoDTO) {
        var emprestimo = emprestimoService.create(emprestimoDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(emprestimo.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowEmprestimoDTO(emprestimo));
    }
}
