package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.categoria.dto.request.EditCategoriaDTO;
import com.example.MeuAlmoxarifado.controller.categoria.dto.request.NewCategoriaDTO;
import com.example.MeuAlmoxarifado.controller.categoria.dto.response.ListCategoriaDTO;
import com.example.MeuAlmoxarifado.controller.categoria.dto.response.ShowCategoriaDTO;
import com.example.MeuAlmoxarifado.service.impl.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping("new")
    public ResponseEntity<ShowCategoriaDTO> create(@RequestBody @Valid NewCategoriaDTO dados, UriComponentsBuilder componentsBuilder) {
        var dto = categoriaService.create(dados);
        var uri = componentsBuilder.path("/categorias/show/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ListCategoriaDTO>> getAll(Pageable pageable, @RequestParam(defaultValue = "") String nome) {
        var page = categoriaService.getAll(pageable, nome);
        return ResponseEntity.ok(page);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowCategoriaDTO> getById(@PathVariable Long id){
        var dto = categoriaService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("edit/{id}")
    public  ResponseEntity<ShowCategoriaDTO> updateById(@PathVariable Long id, @RequestBody @Valid EditCategoriaDTO dados) {
        var dto = categoriaService.updateById(id, dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoriaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
