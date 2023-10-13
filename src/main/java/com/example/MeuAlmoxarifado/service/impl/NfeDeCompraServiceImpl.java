package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.NfeDeCompra;
import com.example.MeuAlmoxarifado.domain.repository.CompraRepository;
import com.example.MeuAlmoxarifado.service.NfeDeCompraService;
import com.example.MeuAlmoxarifado.service.exception.BusinessException;
import com.example.MeuAlmoxarifado.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class NfeDeCompraServiceImpl implements NfeDeCompraService {

    private final CompraRepository compraRepository;

    public NfeDeCompraServiceImpl(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    @Transactional(readOnly = true)
    public List<NfeDeCompra> findAll() {
        return this.compraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public NfeDeCompra findById(Long id) {
        return this.compraRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public NfeDeCompra create(NfeDeCompra nfeDeCompraToCreate) {
        ofNullable(nfeDeCompraToCreate).orElseThrow(() -> new BusinessException("A transação a ser criada não deve ser nula."));


        return this.compraRepository.save(nfeDeCompraToCreate);
    }

    @Transactional
    public NfeDeCompra update(Long id, NfeDeCompra nfeDeCompraToCreate) {
        NfeDeCompra dbNfeDeCompra = this.findById(id);

        if(!dbNfeDeCompra.getId().equals(nfeDeCompraToCreate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        dbNfeDeCompra.setNfe(nfeDeCompraToCreate.getNfe());
        dbNfeDeCompra.setChaveNfe(nfeDeCompraToCreate.getChaveNfe());
        dbNfeDeCompra.setDataEmissao(nfeDeCompraToCreate.getDataEmissao());
        dbNfeDeCompra.setDataRecebimento(nfeDeCompraToCreate.getDataRecebimento());
        dbNfeDeCompra.setValorTotalProdutos(nfeDeCompraToCreate.getValorTotalProdutos());
        dbNfeDeCompra.setValorFrete(nfeDeCompraToCreate.getValorFrete());
        dbNfeDeCompra.setValorTotalIpi(nfeDeCompraToCreate.getValorTotalIpi());
        dbNfeDeCompra.setValorSeguro(nfeDeCompraToCreate.getValorSeguro());
        dbNfeDeCompra.setValorDesconto(nfeDeCompraToCreate.getValorDesconto());
        dbNfeDeCompra.setValorTotalNfe(nfeDeCompraToCreate.getValorTotalNfe());
        dbNfeDeCompra.setValorOutros(nfeDeCompraToCreate.getValorOutros());
        dbNfeDeCompra.setObs(nfeDeCompraToCreate.getObs());
        dbNfeDeCompra.setTransportadora(nfeDeCompraToCreate.getTransportadora());
        dbNfeDeCompra.setFornecedora(nfeDeCompraToCreate.getFornecedora());
        dbNfeDeCompra.setItens(nfeDeCompraToCreate.getItens());




        return this.compraRepository.save(dbNfeDeCompra);
    }

    @Transactional
    public void delete(Long id) {
        NfeDeCompra dbNfeDeCompra = this.findById(id);
        this.compraRepository.delete(dbNfeDeCompra);
    }
}
