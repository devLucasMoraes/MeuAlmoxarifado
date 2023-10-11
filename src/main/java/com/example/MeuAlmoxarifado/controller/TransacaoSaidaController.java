package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.dto.transacaoSaida.request.TransacaoSaidaDTO;
import com.example.MeuAlmoxarifado.controller.dto.transacaoSaida.response.ListTransacaoSaidaDTO;
import com.example.MeuAlmoxarifado.controller.dto.transacaoSaida.response.ShowTransacaoSaidaDTO;
import com.example.MeuAlmoxarifado.service.TransacaoSaidaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/transacoes_saida")
public record TransacaoSaidaController(TransacaoSaidaService transacaoSaidaService) {


    @PostMapping("new")
    public ResponseEntity<ShowTransacaoSaidaDTO> create(@RequestBody @Valid TransacaoSaidaDTO transacaoSaidaDTO) {
        var transacaoSaida = transacaoSaidaService.create(transacaoSaidaDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(transacaoSaida.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowTransacaoSaidaDTO(transacaoSaida));
    }

    @GetMapping
    public ResponseEntity<List<ListTransacaoSaidaDTO>> getAll() {
        var transacoesSaida = transacaoSaidaService.findAll();
        var transacoesSaidaDTO = transacoesSaida.stream().map(ListTransacaoSaidaDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(transacoesSaidaDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowTransacaoSaidaDTO> getById(@PathVariable Long id) {
        var transacaoSaida = transacaoSaidaService.findById(id);
        return ResponseEntity.ok(new ShowTransacaoSaidaDTO(transacaoSaida));
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<ShowTransacaoSaidaDTO> updateById(@PathVariable Long id, @RequestBody @Valid TransacaoSaidaDTO transacaoSaidaDTO) {
        var transacaoSaida = transacaoSaidaService.update(id, transacaoSaidaDTO.toModel());
        return ResponseEntity.ok(new ShowTransacaoSaidaDTO(transacaoSaida));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        transacaoSaidaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
