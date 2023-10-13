package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.dto.categoria.request.CategoriaDTO;
import com.example.MeuAlmoxarifado.controller.dto.categoria.response.ShowCategoriaDTO;
import com.example.MeuAlmoxarifado.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/categorias")
public record CategoriaController(CategoriaService categoriaService) {

    @PostMapping("new")
    public ResponseEntity<ShowCategoriaDTO> create(@RequestBody @Valid CategoriaDTO categoriaDTO) {
        var categoria = categoriaService.create(categoriaDTO.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoria.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowCategoriaDTO(categoria));
    }

    @GetMapping
    public ResponseEntity<List<ShowCategoriaDTO>> getAll() {
        var categorias = categoriaService.findAll();
        var categoriasDTO = categorias.stream().map(ShowCategoriaDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(categoriasDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowCategoriaDTO> getById(@PathVariable Long id){
        var categoria = categoriaService.findById(id);
        return ResponseEntity.ok(new ShowCategoriaDTO(categoria));
    }

    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowCategoriaDTO> updateById(@PathVariable Long id, @RequestBody @Valid CategoriaDTO categoriaDTO) {
        var categoria = categoriaService.update(id, categoriaDTO.toModel());
        return ResponseEntity.ok(new ShowCategoriaDTO(categoria));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
