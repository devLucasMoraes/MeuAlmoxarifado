package com.example.MeuAlmoxarifado.service.impl;

import com.example.MeuAlmoxarifado.domain.model.NfeDeCompra;
import com.example.MeuAlmoxarifado.domain.repository.NfeDeCompraRepository;
import com.example.MeuAlmoxarifado.service.*;
import com.example.MeuAlmoxarifado.service.exception.BusinessException;
import com.example.MeuAlmoxarifado.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NfeDeCompraServiceImpl implements NfeDeCompraService {

    private final NfeDeCompraRepository nfeDeCompraRepository;

    private final MovimentacaoService movimentacaoService;

    private final FornecedoraService fornecedoraService;

    private final TransportadoraService transportadoraService;

    private final MaterialService materialService;

    public NfeDeCompraServiceImpl(NfeDeCompraRepository nfeDeCompraRepository, MovimentacaoService movimentacaoService, FornecedoraService fornecedoraService, TransportadoraService transportadoraService, MaterialService materialService) {
        this.nfeDeCompraRepository = nfeDeCompraRepository;
        this.movimentacaoService = movimentacaoService;
        this.fornecedoraService = fornecedoraService;
        this.transportadoraService = transportadoraService;
        this.materialService = materialService;
    }

    @Transactional(readOnly = true)
    public List<NfeDeCompra> findAll() {
        return this.nfeDeCompraRepository.findAll();
    }

    @Transactional(readOnly = true)
    public NfeDeCompra findById(Long id) {
        return this.nfeDeCompraRepository.findById(id).orElseThrow(() -> new NotFoundException("N-fe"));
    }

    @Transactional
    public NfeDeCompra create(NfeDeCompra nfeDeCompraToCreate) {

        if(!this.fornecedoraService.existsById(nfeDeCompraToCreate.getFornecedora().getId())) {
            throw new NotFoundException("Fornecedora");
        }

        if(!this.transportadoraService.existsById(nfeDeCompraToCreate.getTransportadora().getId())) {
            throw new NotFoundException("Transportadora");
        }

        nfeDeCompraToCreate.getItens().forEach(itemDeCompra -> {
            if(!this.materialService.existsById(itemDeCompra.getMaterial().getId())) {
                throw new NotFoundException("Material");
            }
            itemDeCompra.setNfeDeCompra(nfeDeCompraToCreate);
        });


        NfeDeCompra nfeDeCompraSaved = this.nfeDeCompraRepository.save(nfeDeCompraToCreate);

        this.movimentacaoService.movimentar(nfeDeCompraSaved);

        return nfeDeCompraSaved;
    }

    @Transactional
    public NfeDeCompra update(Long id, NfeDeCompra nfeDeCompraToUpdate) {
        NfeDeCompra dbNfeDeCompra = this.findById(id);

        if(!dbNfeDeCompra.getId().equals(nfeDeCompraToUpdate.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        nfeDeCompraToUpdate.getItens().forEach(itemDeCompra -> {
            if(!this.materialService.existsById(itemDeCompra.getMaterial().getId())) {
                throw new NotFoundException("Material");
            }
            itemDeCompra.setNfeDeCompra(nfeDeCompraToUpdate);
        });

        dbNfeDeCompra.setNfe(nfeDeCompraToUpdate.getNfe());
        dbNfeDeCompra.setChaveNfe(nfeDeCompraToUpdate.getChaveNfe());
        dbNfeDeCompra.setDataEmissao(nfeDeCompraToUpdate.getDataEmissao());
        dbNfeDeCompra.setDataRecebimento(nfeDeCompraToUpdate.getDataRecebimento());
        dbNfeDeCompra.setValorTotalProdutos(nfeDeCompraToUpdate.getValorTotalProdutos());
        dbNfeDeCompra.setValorFrete(nfeDeCompraToUpdate.getValorFrete());
        dbNfeDeCompra.setValorTotalIpi(nfeDeCompraToUpdate.getValorTotalIpi());
        dbNfeDeCompra.setValorSeguro(nfeDeCompraToUpdate.getValorSeguro());
        dbNfeDeCompra.setValorDesconto(nfeDeCompraToUpdate.getValorDesconto());
        dbNfeDeCompra.setValorTotalNfe(nfeDeCompraToUpdate.getValorTotalNfe());
        dbNfeDeCompra.setValorOutros(nfeDeCompraToUpdate.getValorOutros());
        dbNfeDeCompra.setObs(nfeDeCompraToUpdate.getObs());
        dbNfeDeCompra.setTransportadora(nfeDeCompraToUpdate.getTransportadora());
        dbNfeDeCompra.setFornecedora(nfeDeCompraToUpdate.getFornecedora());
        dbNfeDeCompra.getItens().clear();
        dbNfeDeCompra.getItens().addAll(nfeDeCompraToUpdate.getItens());


        NfeDeCompra nfeDeCompraUpdated = this.nfeDeCompraRepository.save(dbNfeDeCompra);

        this.movimentacaoService.movimentar(nfeDeCompraUpdated);

        return nfeDeCompraUpdated;
    }

    @Transactional
    public void delete(Long id) {
        NfeDeCompra dbNfeDeCompra = this.findById(id);
        this.nfeDeCompraRepository.delete(dbNfeDeCompra);
    }
}
