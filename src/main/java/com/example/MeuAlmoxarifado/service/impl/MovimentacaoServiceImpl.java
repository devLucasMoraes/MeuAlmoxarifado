package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.Material;
import com.example.MeuAlmoxarifado.domain.model.Movimentacao;
import com.example.MeuAlmoxarifado.domain.repository.MovimentacaoRepository;
import com.example.MeuAlmoxarifado.service.MaterialService;
import com.example.MeuAlmoxarifado.service.MovimentacaoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class MovimentacaoServiceImpl implements MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;

    private final MaterialService materialService;

    public MovimentacaoServiceImpl(MovimentacaoRepository movimentacaoRepository, MaterialService materialService) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.materialService = materialService;
    }

    @Transactional
    public void registrarEntrada(Movimentacao entrada) {
        Material dbMaterial = this.materialService.findById(entrada.getMaterial().getId());

        BigDecimal qtdEmEstoque = dbMaterial.getQtdEmEstoque();
        BigDecimal valorUntMed = dbMaterial.getValorUntMed();
        BigDecimal valorTotalDoEstoque = qtdEmEstoque.multiply(valorUntMed);

        BigDecimal qtdEmEstoqueAtualizada = qtdEmEstoque.add(entrada.getQuantidade());

        if(qtdEmEstoqueAtualizada.compareTo(BigDecimal.ZERO) != 0){
            BigDecimal valorUnt = valorTotalDoEstoque.add(entrada.getValorTotal())
                    .divide(qtdEmEstoqueAtualizada, 4, RoundingMode.HALF_UP);
            dbMaterial.setValorUntMed(valorUnt);
        }

        dbMaterial.setQtdEmEstoque(qtdEmEstoqueAtualizada);


        movimentacaoRepository.saveAndFlush(entrada);
    }

    @Transactional
    public void registrarSaida(Movimentacao saida) {
        Material dbMaterial = this.materialService.findById(saida.getMaterial().getId());
        BigDecimal qtdEmEstoque = dbMaterial.getQtdEmEstoque();
        dbMaterial.setQtdEmEstoque(qtdEmEstoque.subtract(saida.getQuantidade()));

        movimentacaoRepository.saveAndFlush(saida);
    }
}
