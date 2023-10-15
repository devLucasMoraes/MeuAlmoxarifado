package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.Material;
import com.example.MeuAlmoxarifado.domain.model.Movimentacao;
import com.example.MeuAlmoxarifado.domain.model.NfeDeCompra;
import com.example.MeuAlmoxarifado.domain.model.Tipo;
import com.example.MeuAlmoxarifado.domain.repository.MovimentacaoRepository;
import com.example.MeuAlmoxarifado.service.ConversaoDeCompraService;
import com.example.MeuAlmoxarifado.service.MaterialService;
import com.example.MeuAlmoxarifado.service.MovimentacaoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class MovimentacaoServiceImpl implements MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;

    private final MaterialService materialService;

    private final ConversaoDeCompraService conversaoDeCompraService;

    public MovimentacaoServiceImpl(MovimentacaoRepository movimentacaoRepository, MaterialService materialService, ConversaoDeCompraService conversaoDeCompraService) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.materialService = materialService;
        this.conversaoDeCompraService = conversaoDeCompraService;
    }

    @Transactional
    public void movimentar(NfeDeCompra nfeDeCompra) {
        nfeDeCompra.getItens().forEach(item -> {
            Material dbMaterial = this.materialService.findById(item.getMaterial().getId());
            BigDecimal qtdEmEstoque = dbMaterial.getQtdEmEstoque();
            BigDecimal qtdConvertida = this.conversaoDeCompraService.coverterQuantidadeParaUndidadeDeEstoque(item, nfeDeCompra.getFornecedora(), dbMaterial);
            BigDecimal diferenca = qtdConvertida.subtract(qtdEmEstoque);


            BigDecimal valorUntMed = dbMaterial.getValorUntMed();
            BigDecimal valorTotalDoEstoque = qtdEmEstoque.multiply(valorUntMed);

            BigDecimal valorTotal = item.getValorUntCom().multiply(item.getQuantCom()).add(item.getValorIpi());

            BigDecimal divisor = qtdEmEstoque.add(qtdConvertida);

            BigDecimal valorUnt = valorTotalDoEstoque.add(valorTotal).divide(divisor, 4, RoundingMode.HALF_UP);
            BigDecimal valorTotalMov = qtdConvertida.multiply(valorUnt);

            dbMaterial.setQtdEmEstoque(qtdEmEstoque.add(qtdConvertida));
            dbMaterial.setValorUntMed(valorUnt);

            Movimentacao mov = new Movimentacao();


            mov.setMaterial(dbMaterial);
            mov.setTipo(Tipo.ENTRADA);
            mov.setData(LocalDateTime.now());
            mov.setQuantidade(qtdConvertida);
            mov.setUnidade(dbMaterial.getCategoria().getUndEstoque());
            mov.setValorUnitMed(valorUnt);
            mov.setValorTotal(valorTotalMov);
            mov.setJustificativa("referente a nfe");

            movimentacaoRepository.save(mov);

        });
    }

}
