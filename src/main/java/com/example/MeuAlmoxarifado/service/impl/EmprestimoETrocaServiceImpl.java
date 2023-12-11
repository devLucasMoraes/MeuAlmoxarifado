package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.*;
import com.example.MeuAlmoxarifado.domain.repository.EmprestimoETrocaRepository;
import com.example.MeuAlmoxarifado.service.*;
import com.example.MeuAlmoxarifado.service.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class EmprestimoETrocaServiceImpl implements EmprestimoETrocaService {

    private final EmprestimoETrocaRepository emprestimoETrocaRepository;

    private final FornecedoraService fornecedoraService;

    private final MaterialService materialService;

    private final MovimentacaoService movimentacaoService;

    private final MovimentacaoFactory movimentacaoFactory;

    public EmprestimoETrocaServiceImpl(EmprestimoETrocaRepository emprestimoETrocaRepository, FornecedoraService fornecedoraService, MaterialService materialService, MovimentacaoService movimentacaoService, MovimentacaoFactory movimentacaoFactory) {
        this.emprestimoETrocaRepository = emprestimoETrocaRepository;
        this.fornecedoraService = fornecedoraService;
        this.materialService = materialService;
        this.movimentacaoService = movimentacaoService;
        this.movimentacaoFactory = movimentacaoFactory;
    }

    @Transactional(readOnly = true)
    public Page<EmprestimoETroca> findAll(Pageable pageable) {
        return this.emprestimoETrocaRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public EmprestimoETroca findById(Long id) {
        return this.emprestimoETrocaRepository.findById(id).orElseThrow(() -> new NotFoundException("Empréstimo"));
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
                Movimentacao saida = this.movimentacaoFactory.criarMovimentacaoSaida(item,
                        "Saída de material referente a empréstimo à: Fornecedora id: %s".formatted(emprestimoETrocaToCreate.getFornecedora().getId()));
                this.movimentacaoService.registrarSaidaDoEstoqueFisico(saida);

                item.setValorUnitario(saida.getValorUnt());
            }

            if(emprestimoETrocaToCreate.getTipo().equals(Tipo.ENTRADA)){
                Movimentacao entrada = this.movimentacaoFactory.criarMovimentacaoEntrada(item,
                        "Entrada de material referente a empréstimo da Fornecedora id: %s".formatted(emprestimoETrocaToCreate.getFornecedora().getId()));
                this.movimentacaoService.registrarEntradaAoEstoqueFisico(entrada);

                item.setValorUnitario(entrada.getValorUnt());
            }


        });

        BigDecimal valorTotalItens = emprestimoETrocaToCreate.getItensEmprestimo().stream().map(ItemEmprestimoETroca::getValorUnitario).reduce(BigDecimal.ZERO, BigDecimal::add);
        emprestimoETrocaToCreate.setValorTotal(valorTotalItens);

        return this.emprestimoETrocaRepository.save(emprestimoETrocaToCreate);
    }

    public EmprestimoETroca update(Long aLong, EmprestimoETroca entity) {
        return null;
    }

    public void delete(Long aLong) {

    }

}
