package com.example.MeuAlmoxarifado.controller;


import com.example.MeuAlmoxarifado.controller.dto.request.EmprestimoETrocaDTO;
import com.example.MeuAlmoxarifado.controller.dto.request.RequisicaoDeEstoqueDTO;
import com.example.MeuAlmoxarifado.controller.dto.response.ShowEmprestimoETrocaDTO;
import com.example.MeuAlmoxarifado.controller.dto.response.ShowRequisicaoDeEstoqueDTO;
import com.example.MeuAlmoxarifado.service.EmprestimoETrocaService;
import com.example.MeuAlmoxarifado.service.RequisicaoDeEstoqueService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/emprestimos-e-trocas")
@CrossOrigin
public record EmprestimoETrocaController(EmprestimoETrocaService emprestimoETrocaService) {


    @PostMapping("new")
    public ResponseEntity<ShowEmprestimoETrocaDTO> create(@RequestBody @Valid EmprestimoETrocaDTO emprestimoETrocaDTO) {
        var emprestimoTroca = emprestimoETrocaService.create(emprestimoETrocaDTO.toNewModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(emprestimoTroca.getId())
                .toUri();
        return ResponseEntity.created(location).body(new ShowEmprestimoETrocaDTO(emprestimoTroca));
    }

    @GetMapping
    public ResponseEntity<Page<ShowEmprestimoETrocaDTO>> getAll(Pageable pageable) {
        var emprestimoTroca = emprestimoETrocaService.findAll(pageable);
        var emprestimoETrocaDTO = emprestimoTroca.map(ShowEmprestimoETrocaDTO::new);
        return ResponseEntity.ok(emprestimoETrocaDTO);
    }

    @GetMapping("show/{id}")
    public ResponseEntity<ShowEmprestimoETrocaDTO> getById(@PathVariable Long id) {
        var emprestimoTroca = emprestimoETrocaService.findById(id);
        return ResponseEntity.ok(new ShowEmprestimoETrocaDTO(emprestimoTroca));
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<ShowEmprestimoETrocaDTO> updateById(@PathVariable Long id, @RequestBody @Valid EmprestimoETrocaDTO emprestimoETrocaDTO) {
        var emprestimoTroca = emprestimoETrocaService.update(id, emprestimoETrocaDTO.toModel());
        return ResponseEntity.ok(new ShowEmprestimoETrocaDTO(emprestimoTroca));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        emprestimoETrocaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
