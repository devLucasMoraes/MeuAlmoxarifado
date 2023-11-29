package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.*;
import com.example.MeuAlmoxarifado.domain.repository.EmprestimoETrocaRepository;
import com.example.MeuAlmoxarifado.service.EmprestimoETrocaService;
import com.example.MeuAlmoxarifado.service.FornecedoraService;
import com.example.MeuAlmoxarifado.service.MaterialService;
import com.example.MeuAlmoxarifado.service.MovimentacaoService;
import com.example.MeuAlmoxarifado.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class EmprestimoETrocaServiceImpl implements EmprestimoETrocaService {

    private final EmprestimoETrocaRepository emprestimoETrocaRepository;

    private final FornecedoraService fornecedoraService;

    private final MaterialService materialService;

    private final MovimentacaoService movimentacaoService;

    public EmprestimoETrocaServiceImpl(EmprestimoETrocaRepository emprestimoETrocaRepository, FornecedoraService fornecedoraService, MaterialService materialService, MovimentacaoService movimentacaoService) {
        this.emprestimoETrocaRepository = emprestimoETrocaRepository;
        this.fornecedoraService = fornecedoraService;
        this.materialService = materialService;
        this.movimentacaoService = movimentacaoService;
    }

    @Transactional(readOnly = true)
    public Page<EmprestimoETroca> findAll(Pageable pageable) {
        return this.emprestimoETrocaRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public EmprestimoETroca findById(Long id) {
        return this.emprestimoETrocaRepository.findById(id).orElseThrow(() -> new NotFoundException("Emprestimo"));
    }

    @Transactional
    public EmprestimoETroca create(EmprestimoETroca emprestimoETrocaToCreate) {
        if (!this.fornecedoraService.existsById(emprestimoETrocaToCreate.getFornecedora().getId())) {
            throw new NotFoundException("Fornecedora");
        }

        emprestimoETrocaToCreate.getItensEmprestimo().forEach(item -> {
            if (!this.materialService.existsById(item.getMaterial().getId())) {
                throw new NotFoundException("Material");
            }
            item.setEmprestimoETroca(emprestimoETrocaToCreate);

            if(emprestimoETrocaToCreate.getTipo().equals(Tipo.SAIDA)){
                Movimentacao saida = criarMovimentacaoSaida(item, "Emprestando materail a: Forecedora id: %s"
                        .formatted(emprestimoETrocaToCreate.getFornecedora().getId()));
                this.movimentacaoService.registrarSaidaAoEstoqueFisico(saida);

                item.setValorUnt(saida.getValorUnt());
            }

            if(emprestimoETrocaToCreate.getTipo().equals(Tipo.ENTRADA)){
                Movimentacao entrada = criarMovimentacaoEntrada(item, "Forecedora id: %s, Emprestou material"
                        .formatted(emprestimoETrocaToCreate.getFornecedora().getId()));
                this.movimentacaoService.registrarEntradaAoEstoqueFisico(entrada);

                item.setValorUnt(entrada.getValorUnt());
            }


        });

        BigDecimal valorTotalItens = emprestimoETrocaToCreate.getItensEmprestimo().stream().map(ItemEmprestimoETroca::getValorUnt).reduce(BigDecimal.ZERO, BigDecimal::add);
        emprestimoETrocaToCreate.setValorTotal(valorTotalItens);

        return this.emprestimoETrocaRepository.save(emprestimoETrocaToCreate);
    }

    public EmprestimoETroca update(Long aLong, EmprestimoETroca entity) {
        return null;
    }

    public void delete(Long aLong) {

    }

    private Movimentacao criarMovimentacaoSaida(ItemEmprestimoETroca item, String justificativa) {
        Material dbMaterial = this.materialService.findById(item.getMaterial().getId());

        BigDecimal valorTotalEntregue = dbMaterial.getValorUntMed().multiply(item.getQuantEnt());

        Movimentacao saida = new Movimentacao();
        saida.setTipo(Tipo.SAIDA);
        saida.setMaterial(dbMaterial);
        saida.setData(LocalDateTime.now());
        saida.setQuantidade(item.getQuantEnt());
        saida.setUnidade(dbMaterial.getCategoria().getUndEstoque());
        saida.setValorUnt(dbMaterial.getValorUntMed());
        saida.setValorTotal(valorTotalEntregue);
        saida.setJustificativa(justificativa);
        return saida;
    }

    private Movimentacao criarMovimentacaoEntrada(ItemEmprestimoETroca item, String justificativa) {
        Material dbMaterial = this.materialService.findById(item.getMaterial().getId());


        Movimentacao entrada = new Movimentacao();
        entrada.setTipo(Tipo.ENTRADA);
        entrada.setMaterial(dbMaterial);
        entrada.setData(LocalDateTime.now());
        entrada.setQuantidade(item.getQuantEnt());
        entrada.setUnidade(dbMaterial.getCategoria().getUndEstoque());
        entrada.setValorUnt(BigDecimal.ZERO);
        entrada.setValorTotal(BigDecimal.ZERO);
        entrada.setJustificativa(justificativa);
        return entrada;
    }
}
