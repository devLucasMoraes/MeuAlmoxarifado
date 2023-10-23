package com.example.MeuAlmoxarifado.controller;

import com.example.MeuAlmoxarifado.controller.dto.request.LocalDeAplicacaoDTO;
import com.example.MeuAlmoxarifado.controller.dto.response.ShowLocalDeAplicacaoDTO;
import com.example.MeuAlmoxarifado.service.LocalDeAplicacaoService;
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
public record LocalDeAplicacaoController(LocalDeAplicacaoService localDeAplicacaoService) {

    @PostMapping("new")
    public ResponseEntity<ShowLocalDeAplicacaoDTO> create(@RequestBody @Valid LocalDeAplicacaoDTO localDeAplicacaoDTO) {
        var localDeAplicacao = localDeAplicacaoService.create(localDeAplicacaoDTO.toNewModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(localDeAplicacao.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowLocalDeAplicacaoDTO(localDeAplicacao));
    }

    @GetMapping
    public ResponseEntity<List<ShowLocalDeAplicacaoDTO>> getAll() {
        var locaisDeAplicacao = localDeAplicacaoService.findAll();
        var locaisDeAplicacaoDTO = locaisDeAplicacao.stream().map(ShowLocalDeAplicacaoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(locaisDeAplicacaoDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowLocalDeAplicacaoDTO> getById(@PathVariable Long id){
        var localDeAplicacao = localDeAplicacaoService.findById(id);
        return ResponseEntity.ok(new ShowLocalDeAplicacaoDTO(localDeAplicacao));
    }


    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowLocalDeAplicacaoDTO> updateById(@PathVariable Long id, @RequestBody @Valid LocalDeAplicacaoDTO localDeAplicacaoDTO) {
        var localDeAplicacao = localDeAplicacaoService.update(id, localDeAplicacaoDTO.toModel());
        return ResponseEntity.ok(new ShowLocalDeAplicacaoDTO(localDeAplicacao));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        localDeAplicacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
