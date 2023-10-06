package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.transacaoSaida.dto.request.EditTransacaoSaidaDTO;
import com.example.MeuAlmoxarifado.controller.transacaoSaida.dto.request.NewTransacaoSaidaDTO;
import com.example.MeuAlmoxarifado.controller.transacaoSaida.dto.response.ListTransacaoSaidaDTO;
import com.example.MeuAlmoxarifado.controller.transacaoSaida.dto.response.ShowTransacaoSaidaDTO;
import com.example.MeuAlmoxarifado.service.impl.TransacaoSaidaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/transacoes_saida")
public class TransacaoSaidaController {

    @Autowired
    private TransacaoSaidaService transacaoSaidaService;

    @PostMapping("new")
    public ResponseEntity<ShowTransacaoSaidaDTO> create(@RequestBody @Valid NewTransacaoSaidaDTO dados, UriComponentsBuilder componentsBuilder) {
        var dto = transacaoSaidaService.create(dados);
        var uri = componentsBuilder.path("/transacoes_saida/show/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ListTransacaoSaidaDTO>> getAll(Pageable pageable) {
        var page = transacaoSaidaService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowTransacaoSaidaDTO> getById(@PathVariable Long id) {
        var dto = transacaoSaidaService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowTransacaoSaidaDTO> updateById(@PathVariable Long id, @RequestBody @Valid EditTransacaoSaidaDTO dados) {
        var dto = transacaoSaidaService.updateById(id,dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        transacaoSaidaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
